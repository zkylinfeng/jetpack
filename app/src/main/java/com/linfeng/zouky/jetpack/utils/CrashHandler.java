package com.linfeng.zouky.jetpack.utils;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.linfeng.zouky.liibrary.utils.Constants;
import com.linfeng.zouky.liibrary.utils.PublicUtil;
import com.linfeng.zouky.liibrary.utils.Static;
import com.linfeng.zouky.liibrary.utils.WriteLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mCrashHandler;
    private Handler mHandler = null;

    public static CrashHandler getInstance() {
        if (mCrashHandler == null) {
            mCrashHandler = new CrashHandler();
            Thread.setDefaultUncaughtExceptionHandler(mCrashHandler);
        }
        return mCrashHandler;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        //上传日志文件
        final String error = Log.getStackTraceString(throwable);
        WriteLog.Error(error);
        WriteLog.Info(Thread.currentThread().getName());
        uploadErrorLog(error);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String content = "手机型号:" + Build.MODEL
                        + "\r\n产品型号:" + Build.PRODUCT
                        + "\r\nCPU类型:" + Build.CPU_ABI
                        + "\r\n系统版本:" + Build.VERSION.RELEASE
                        + "\r\nSDK版本号:" + Build.VERSION.SDK_INT
                        + "\r\n系统定制商:" + Build.BRAND
                        + "\r\nROM制造商:" + Build.MANUFACTURER
                        + "\r\n";
                File fileAll = buildLogFile(content, "ErrorLogAndroid.txt");
                if (fileAll.exists()) {
                    addContent(fileAll, error);
                    mHandler.sendEmptyMessage(0);
                } else {
                    PublicUtil.showShort(Static.context,
                            "文件创建！请检查SD卡是否插好，读写权限是否开启,应用即将关闭");
                    mHandler.sendEmptyMessage(2);
                }
            }
        });
    }


    public void uploadErrorLog(String errorLog) {
        File file = buildLogFile(errorLog, "tempErrorLogAndroid.txt");

    }

    private File buildLogFile(String content, String fileName) {
        File file = new File(Constants.LOG_ROOT_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        File logFile = new File(Constants.LOG_ROOT_PATH, fileName);
        if (!logFile.exists()) {
            try {
                if (logFile.createNewFile()) {
                    writeTitle(content, logFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logFile;
    }

    /**
     * 追加文件：使用FileWriter
     */
    private void addContent(File file, String content) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String data = formatter.format(curDate);
        String str = content.replace("\n", "\r\n");
        try {
            // 打开一个FileWriter，追加书写
            FileWriter writer = new FileWriter(file, true);
            writer.write("\r\n");
            writer.write(data);
            writer.write("\r\n");
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            WriteLog.Error("写入文件失败：" + e);
        }
    }

    private void writeTitle(String content, File file) {
        try {
            // 打开一个FileWriter，覆盖书写
            FileWriter writer = new FileWriter(file, false);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
