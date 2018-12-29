package com.linfeng.zouky.liibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;

/**
 * @author zouky
 */
public class PublicUtil {
    public static void showShort(String str) {
        Toast.makeText(Static.context, str, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int stringId) {
        Toast.makeText(context, context.getString(stringId), Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int[] ids) {
        StringBuilder builder = new StringBuilder();
        for (int id : ids) {
            builder.append(context.getString(id));
        }
        Toast.makeText(context, builder.toString(), Toast.LENGTH_SHORT).show();
    }


    /**
     * 得到当前显示的activity
     * @param context
     * @return
     */
    public static String getCurrentActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(android.content
                .Context.ACTIVITY_SERVICE);
        ComponentName componentName = manager.getRunningTasks(1).get(0).topActivity;
        return componentName.getClassName();
    }




    //保存屏幕参数
    public static void saveDisplayMetrics(Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences("DisplayMetrics", 0);
        DisplayMetrics m = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(m);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("density", m.density);
        editor.putInt("height", m.heightPixels);
        editor.putInt("width", m.widthPixels);
        WriteLog.Info("density=" + m.density + ";height=" + m.heightPixels + ";width=" + m
                .widthPixels);
        editor.commit();
    }

    //获取屏幕密度
    public static float getDisplayDensity(Context context) {
        SharedPreferences sp = context.getSharedPreferences("DisplayMetrics", 0);
        return sp.getFloat("density", 0.0f);
    }

    //获取屏幕高度
    public static int getDisplayHeight(Context context) {
        SharedPreferences sp = context.getSharedPreferences("DisplayMetrics", 0);
        return sp.getInt("height", 0);
    }

    //获取屏幕宽度
    public static int getDisplayWidth(Context context) {
        SharedPreferences sp = context.getSharedPreferences("DisplayMetrics", 0);
        return sp.getInt("width", 0);
    }

    //收起软键盘
    public static void packUpKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            if (activity.getCurrentFocus() != null) {
                try {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //弹出软键盘
    public static void showKeyboard(Activity activity) {
        /*如果顶部视图可见高度大于2/3屏幕高度，则认定软键盘未弹出（大约计算）*/
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        if ((rect.bottom - rect.top) > (getDisplayHeight(activity) * 0.667)) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context
                    .INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    //获取APP版本号
    public static int getAppVersionCode(Context context) {
        PackageInfo packInfo = getPackageInfo(context);
        if (packInfo != null) {
            return packInfo.versionCode;
        } else {
            return 0;
        }
    }

    public static String getAppVersionName(Context context) {
        PackageInfo packInfo = getPackageInfo(context);
        if (packInfo != null) {
            return packInfo.versionName;
        } else {
            return "";
        }
    }


    /**
     * 判断是否安装应用
     *
     * @param pkgName
     * @return
     */
    public static boolean isPkgInstalled(String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = Static.context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void openApp(String pkgName, Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo pi = context.getPackageManager().getPackageInfo(pkgName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);

            List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                String packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                ComponentName cn = new ComponentName(packageName, className);
                intent.setAction("android.intent.action.MAIN");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(cn);
                context.startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取出字符串里面的数字拼接在一起
     * @param str
     * @return
     */
    public static String getNumber(String str) {
        if (StrUtil.isBlank(str)) {
            return "";
        }
        str = str.trim();
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                str2 += str.charAt(i);
            }
        }
        return str2;
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static boolean isUseable(Activity mActivity) {
        if ((null == mActivity) || mActivity.isFinishing() || mActivity.isRestricted()) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                if (mActivity.isDestroyed()) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
