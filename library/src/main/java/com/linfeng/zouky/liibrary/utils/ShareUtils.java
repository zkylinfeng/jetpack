package com.linfeng.zouky.liibrary.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zouky
 * Time :2017/10/17
 * Description:
 */
public class ShareUtils {



  /*  public static void showOffLineToast()
    {
        if (isShowOffLine(System.currentTimeMillis()))
        {
            ToastUtils.ShowToastTop();
            setStarTime("START_OFFLINE");
        }
    }*/

    public static void showNetToast() {
        if (isShowToast(System.currentTimeMillis())) {
            // ToastUtils.ShowToast(Static.CONTEXT.getString(R.string.network_bad_info));
        }
    }

    public static void setStarTime(String key) {
        setPreferLong(key, System.currentTimeMillis());
    }

    public static void setStarTime(String key, long time) {
        setPreferLong(key, time);
    }

    @TargetApi(19)
    public static boolean isShowToast(long end) {
        boolean flag = false;
        if (getPreferBool("TOAST", true)) {
            setPreferBool("TOAST", false);
            flag = true;
        } else {
            long start = getPreferLong("START_TOAST");
            long duration = end - start;
            int a = Long.compare(duration, 60000L);
            if (a > 0) {
                flag = true;
            }
        }
        return flag;
    }

    @TargetApi(19)
    public static boolean isShowOffLine(long end) {
        boolean flag = false;
        if (getPreferBool("OFFlINE", true)) {
            setPreferBool("OFFlINE", false);
            flag = true;
        } else {
            long start = getPreferLong("START_OFFLINE");
            long duration = end - start;
            int a = Long.compare(duration, 60000L);
            if (a > 0) {
                flag = true;
            }
        }
        return flag;
    }

    public static String getUserId() {
        return getPreferStr("USERID");
    }

    public static String getToken() {
        return getPreferStr("TOKEN");
    }

    public static String getUmengToken() {
        return getPreferStr("UMENG_TOKEN");
    }

    public static String getAndroidUUID() {
        return getPreferStr("ANDROID_ID");
    }

    public static String getIMEI() {
        return getPreferStr("IMEI");
    }

    public static String getLongitude() {
        return getPreferStr("LONGITUDE");
    }

    public static String getLatitude() {
        return getPreferStr("LATITUDE");
    }

    public static String getAdress() {
        return getPreferStr("ADDRESS");
    }

    public static String getLocation() {
        return getPreferStr("LOCATION");
    }

    public static String getCityCode() {
        return getPreferStr("CITYCODE");
    }

    public static String getCityName() {
        return getPreferStr("CITYNAME");
    }

    public static int getScreenWidth() {
        return getPreferInt("SWIDTH", 480);
    }

    public static int getScreenHeight() {
        return getPreferInt("SHEIGHT", 800);
    }

    public static String getDpiFloat() {
        return getPreferStr("DPI_FLOAT");
    }

    public static int getDpiInt() {
        return getPreferInt("DPI_INT", 240);
    }

    public static int getSdkVersion() {
        return getPreferInt("SDK_VERSION", 1);
    }

    public static String getVersionName() {
        return getPreferStr("VERSION_NAME");
    }

    public static int getVersionCode() {
        return getPreferInt("VERSION_NO", 1);
    }

    public static String getPackageName() {
        return getPreferStr("PACKAGE_NAME");
    }

    public static int getMemorySize() {
        return getPreferInt("MEMORY_SIZE", 8388608);
    }

    public static int getMemoryMax() {
        return getPreferInt("MEMORY_MAX", 16777216);
    }

    public static boolean getIsLargeScreen() {
        return getPreferBool("ISLARGESCREEN", false);
    }

    public static boolean getIsXLarge() {
        return getPreferBool("ISXLARGE", false);
    }

    public static boolean getIsXXLarge() {
        return getPreferBool("ISLARGESCREEN", false);
    }

    public static String getPackageName(Context mthis) {
        String str = "";
        PackageManager manager = mthis.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(mthis.getPackageName(), 0);
            str = info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void setPrefer(SharedPreferences sp, String key, String strContent) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, strContent);

        editor.commit();
    }

    public static String getPrefer(SharedPreferences sp, String key) {
        String str = sp.getString(key, null);
        return str;
    }

    public static void delPrefer(Context mthis, String key) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);

        editor.commit();
    }

    public static void delAll(Context mthis) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        SharedPreferences.Editor editor = sp.edit();

        editor.commit();
    }

    public static void delPrefer(SharedPreferences sp, String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);

        editor.commit();
    }

    public static void delAllPrefer(SharedPreferences sp) {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();

        editor.commit();
    }

    public static void setPreferStr(String fileName, String key, String content) {
        setPreferStr(Static.context, fileName, key, content);
    }

    public static void setPreferStr(Context mthis, String fileName, String key, String content) {
        SharedPreferences sp = mthis.getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, content);

        editor.commit();
    }

    public static String getPreferStr(String fileName, String key) {
        return getPreferStr(Static.context, fileName, key);
    }

    public static String getPreferStr(Context mthis, String fileName, String key) {
        SharedPreferences sp = mthis.getSharedPreferences(fileName, 0);
        return sp.getString(key, null);
    }

    public static void setPreferStr(String key, String content) {
        setPreferStr(Static.context, key, content);
    }

    public static void setPreferStr(Context mthis, String key, String content) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, content);

        editor.commit();
    }

    public static String getPreferStr(String key) {
        return getPreferStr(Static.context, key);
    }

    public static String getPreferStr(Context mthis, String key) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        return sp.getString(key, null);
    }

    public static void setPreferBool(String key, boolean content) {
        setPreferBool(Static.context, key, content);
    }

    public static void setPreferBool(Context mthis, String key, boolean content) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, content);

        editor.commit();
    }

    public static boolean getPreferBool(String key, boolean defValue) {
        return getPreferBool(Static.context, key, defValue);
    }

    public static boolean getPreferBool(Context mthis, String key, boolean defValue) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        return sp.getBoolean(key, defValue);
    }

    public static int getPreferInt(String key, int defValue) {
        return getPreferInt(Static.context, key, defValue);
    }

    public static int getPreferInt(Context mthis, String key, int defValue) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        return sp.getInt(key, defValue);
    }

    public static void setPreferInt(String key, int content) {
        setPreferInt(Static.context, key, content);
    }

    public static void setPreferInt(Context mthis, String key, int content) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, content);

        editor.commit();
    }

    public static long getPreferLong(String key) {
        return getPreferLong(Static.context, key, 0L);
    }

    public static long getPreferLong(String key, long defValue) {
        return getPreferLong(Static.context, key, defValue);
    }

    public static long getPreferLong(Context mthis, String key, long defValue) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        return sp.getLong(key, defValue);
    }

    public static void setPreferLong(String key, long content) {
        setPreferLong(Static.context, key, content);
    }

    public static void setPreferLong(Context mthis, String key, long content) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, content);

        editor.commit();
    }

    public static float getPreferFloat(String key) {
        return getPreferFloat(Static.context, key, 0.0F);
    }

    public static float getPreferFloat(String key, float defValue) {
        return getPreferFloat(Static.context, key, defValue);
    }

    public static float getPreferFloat(Context mthis, String key, float defValue) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        return sp.getFloat(key, defValue);
    }

    public static void setPreferFloat(String key, float content) {
        setPreferFloat(Static.context, key, content);
    }

    public static void setPreferFloat(Context mthis, String key, float content) {
        SharedPreferences sp = mthis.getSharedPreferences(getPackageName(mthis), 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, content);

        editor.commit();
    }

    public static <T> T getObj(String key, Class<T> clazz) {
        T t = null;
        try {
            String str = getPreferStr("OBJ_TO_JSON", key);
            if (StrUtil.isNotBlank(str)) {
                t = new Gson().fromJson(str, clazz);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return t;
    }

    public static <T> void setObj(String key, T t) {
        String str = "";
        if (t != null) {
            str = new Gson().toJson(t);
            // str = JSON.toJSONString(t);
        }
        setPreferStr("OBJ_TO_JSON", key, str);
    }

    public static <T> List<T> getListObj(String key, Class<T> clazz) {
        List<T> t = null;
        try {
            String str = getPreferStr("LIST_TO_JSON", key);
            if (StrUtil.isNotBlank(str)) {
                Type type = new TypeToken<ArrayList<T>>() {
                }.getType();
                t = new Gson().fromJson(str, type);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return t;
    }

    public static <T> void setListObj(String key, T t) {
        String str = "";
        if (t != null) {
            str = new Gson().toJson(t);
        }
        setPreferStr("LIST_TO_JSON", key, str);
    }

    public static <T> Map<String, T> getMap(String key) {
        Map<String, T> map = null;
        try {
            String str = getPreferStr("MAP_TO_JSON", key);
            if (StrUtil.isNotBlank(str)) {
                Type type = new TypeToken<HashMap<String, T>>() {
                }.getType();
                map = new Gson().fromJson(str, type);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return map;
    }

    public static <T> void setMap(String key, T t) {
        String str = "";
        if (t != null) {
            str = new Gson().toJson(t);
        }
        setPreferStr("MAP_TO_JSON", key, str);
    }

    public static String getLanguage() {
        return getPreferStr("LANGUAGE");
    }

    public static void setLanguage(String language) {
        setPreferStr("LANGUAGE", language);
    }

    public static String getIP() {
        return getPreferStr("IPADDRESS");
    }

    public static void setIP(String language) {
        setPreferStr("IPADDRESS", language);
    }

    public static int getDbVersion() {
        return getPreferInt("DB_VERSION", 1);
    }

    public static void setDbVersion(int newVersion) {
        setPreferInt("DB_VERSION", newVersion);
    }
}

