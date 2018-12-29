package com.linfeng.zouky.jetpack;

import com.linfeng.zouky.jetpack.utils.CrashHandler;
import com.linfeng.zouky.liibrary.BaseApplication;

/**
 * @author :zouky
 * time :2018/12/27
 * Description :
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //日志收集
       CrashHandler.getInstance();
    }
}
