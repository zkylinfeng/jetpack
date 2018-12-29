package com.linfeng.zouky.liibrary.utils;

import android.util.Log;

import com.linfeng.zouky.liibrary.BuildConfig;


/**
 * @author log
 */
public class WriteLog {
    public static void Info(String sWriteStr) {
        if (BuildConfig.DEBUG && StrUtil.isNotBlank(sWriteStr)) {
            Log.i(Constants.TAG, sWriteStr);
        }
    }

    public static void Warn(String sWriteStr) {
        if (BuildConfig.DEBUG && StrUtil.isNotBlank(sWriteStr)) {
            Log.w(Constants.TAG, sWriteStr);
        }
    }

    public static void Verbose(String sWriteStr) {
        if (BuildConfig.DEBUG && StrUtil.isNotBlank(sWriteStr)) {
            Log.v(Constants.TAG, sWriteStr);
        }
    }

    public static void Debug(String sWriteStr) {
        if (BuildConfig.DEBUG && StrUtil.isNotBlank(sWriteStr)) {
            Log.d(Constants.TAG, sWriteStr);
        }
    }

    public static void Error(String sWriteStr) {
        if ( StrUtil.isNotBlank(sWriteStr)) {
            Log.e(Constants.TAG, sWriteStr);
        }
    }
}
