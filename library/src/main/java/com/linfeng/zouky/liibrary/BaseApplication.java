package com.linfeng.zouky.liibrary;

import android.app.Application;
import android.view.LayoutInflater;

import com.linfeng.zouky.liibrary.utils.Static;

/**
 * @author :zouky
 * time :2018/12/27
 * Description :
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Static.context = this;
        Static.inflater = LayoutInflater.from(this);
    }
}
