package com.linfeng.zouky.liibrary.communication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.linfeng.zouky.liibrary.R;
import com.linfeng.zouky.liibrary.utils.PublicUtil;
import com.linfeng.zouky.liibrary.utils.StrUtil;
import com.linfeng.zouky.liibrary.utils.WriteLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetCallBack<T> implements Callback<T> {
    private Operate<T> operate;
    private Context context;
    private NetCallBack<T> netCallBack;
    private boolean autoToast = true;
    public static int SERVER_EXCEPTION = 1;
    public static int JSON_EXCEPTION = 2;
    public static int DATA_EXCEPTION = 3;


    public NetCallBack(Context context, Operate<T> operate) {
        this(context, true, operate);
    }

    public NetCallBack(Context context, boolean autoToast, Operate<T> operate) {
        this.context = context;
        this.operate = operate;
        this.autoToast = autoToast;
        netCallBack = this;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.body() != null || response.code() == 200) {
            WriteLog.Info("NetCallBack-success:" + new Gson().toJson(response.body()));
            operate.success(response.body());
        } else {
            int code;
            String str;
            try {
                String jsonString = response.errorBody().string();
                WriteLog.Error("NetCallBack-errorBody:" + jsonString);
                if (StrUtil.isBlank(jsonString)) {
                    code = SERVER_EXCEPTION;
                    str = context.getString(R.string.request_fail)
                            + "," + context.getString(R.string.retry_after);
                } else {
                    try {
                        JSONObject object = new JSONObject(jsonString);
                        code = object.getInt("status");
                        str = object.getString("message");
                    } catch (JSONException e) {
                        WriteLog.Error("NetCallBack-JSONException:" + e);
                        code = JSON_EXCEPTION;
                        str = context.getString(R.string.server_return) + context.getString(R
                                .string.data_analysis_exception);
                    }
                }
            } catch (IOException e) {
                WriteLog.Error("NetCallBack-IOException:" + e);
                code = DATA_EXCEPTION;
                str = context.getString(R.string.server_return) + context.getString(R.string
                        .data_exception);
            } catch (NullPointerException e) {
                code = JSON_EXCEPTION;
                str = context.getString(R.string.server_return) + context.getString(R.string
                        .data_analysis_exception);
            }

            if (autoToast) {
                PublicUtil.showShort(context, str);
            }
            operate.fail(code, str);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        WriteLog.Error("NetCallBack-onFailure:" + throwable);
        String str;
        if (throwable.toString().contains("TimeoutException")) {
            str = context.getString(R.string.connect_time_out) + "," +
                    context.getString(R.string.retry_after);
        } else {
            str = context.getString(R.string.connect_error);
        }

        if (autoToast) {
            PublicUtil.showShort(context, str);
        }
        operate.error(throwable, str);
    }

    public interface Operate<T> {
        void success(T body);

        void fail(int code, String description);

        void error(Throwable throwable, String description);
    }


}
