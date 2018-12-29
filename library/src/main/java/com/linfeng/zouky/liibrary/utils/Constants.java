package com.linfeng.zouky.liibrary.utils;

import android.os.Environment;

/**
 * @author :zouky
 * time :2018/12/27
 * Description :
 */
public class Constants {

    public final static String TAG = "com.linfeng.zouky";
    /**
     * 每个接口都要加的服务器接口版本号
     */
    public static final String VERSION_CODE = "v1";
    public static final String SDDIR_ROOT_PATH = Environment.getExternalStorageDirectory().getPath()
            + "/Android/data/com.bocs.bmc/bmc_cache";
    /**
     * 日志文件夹
     */
    public static final String LOG_ROOT_PATH = SDDIR_ROOT_PATH + "/log";
}
