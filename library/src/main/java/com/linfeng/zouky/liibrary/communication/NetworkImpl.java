package com.linfeng.zouky.liibrary.communication;

import com.google.gson.Gson;

public class NetworkImpl {
    private static NetworkImpl mNetwork;
    private RetrofitHelper mHelper;
    private RetrofitInterfaces mNormalRetrofitImpl;//超时10秒
    private RetrofitInterfaces mShortRetrofitImpl;//超时3秒
    private RetrofitInterfaces mTokenRetrofitImpl;//
    private Gson mGson;

    private NetworkImpl() {
        mNetwork = this;
        mGson = new Gson();
        mHelper = new RetrofitHelper();
        RetrofitHelper helper = new RetrofitHelper();
        mNormalRetrofitImpl = helper.getRetrofit(RetrofitHelper.TYPE_NORMAL, false).create
                (RetrofitInterfaces.class);
        mShortRetrofitImpl = helper.getRetrofit(RetrofitHelper.TYPE_SHORT, false).create
                (RetrofitInterfaces.class);
        mTokenRetrofitImpl = helper.getRetrofit(RetrofitHelper.TYPE_NORMAL, true).create
                (RetrofitInterfaces.class);
    }

    public static NetworkImpl getInstance() {
        if (mNetwork == null) {
            new NetworkImpl();
        }
        return mNetwork;
    }



    public RetrofitInterfaces getNormalRetrofit() {
        return mNormalRetrofitImpl;
    }

    public RetrofitInterfaces getmTokenRetrofitImpl() {
        return mTokenRetrofitImpl;
    }

    public RetrofitInterfaces getmShortRetrofitImpl() {
        return mShortRetrofitImpl;
    }
}
