package com.linfeng.zouky.liibrary.communication;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.linfeng.zouky.liibrary.utils.Constants;
import com.linfeng.zouky.liibrary.utils.UrlRoot;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    static int TYPE_NORMAL = 0;
    static int TYPE_SHORT = 1;

    //定制
    Retrofit customize(int timeOut, Boolean isRefreshToken) {
        return customize(timeOut, GsonConverterFactory.create(), new NullOnEmptyConvertFactory(), getInterceptor(isRefreshToken));
    }

    //定制
    Retrofit customize(int timeOut, Interceptor interceptor) {
        return customize(timeOut, GsonConverterFactory.create(), new NullOnEmptyConvertFactory(), interceptor);
    }

    //定制
    Retrofit customize(int timeOut, retrofit2.Converter.Factory factory, NullOnEmptyConvertFactory emptyFactory, Interceptor interceptor) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(UrlRoot.SERVERADDR);
        OkHttpClient client;
        if (interceptor == null) {
            client = initClient(timeOut);
        } else {
            client = initClient(timeOut, interceptor);
        }
        if (factory == null) {
            return builder
                    .client(client)
                    .build();
        } else {
            return builder
                    .client(client)
                    .addConverterFactory(emptyFactory)
                    .addConverterFactory(factory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

    }


    Retrofit getRetrofit(int type, Boolean isRefreshToken) {
        Retrofit retrofit;
        if (type == RetrofitHelper.TYPE_SHORT) {
            retrofit = customize(3, false);
        } else {
            retrofit = customize(10, false);
        }
        if (isRefreshToken) {
            retrofit = customize(10, true);
        }
        return retrofit;
    }

    private OkHttpClient initClient(int timeOut) {
        return new OkHttpClient
                .Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .build();
    }

    private OkHttpClient initClient(int timeOut, Interceptor interceptor) {
        return new OkHttpClient
                .Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .addInterceptor(appInterceptor)
                .addNetworkInterceptor(interceptor)
                .build();
    }

    private Interceptor getInterceptor(boolean isRefreshToken) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = "";
                Request request = chain
                        .request()
                        .newBuilder()
                        .header("access-token", token)
                        .header("version", Constants.VERSION_CODE)
                        .header("Accept", "application/json;charset=UTF-8")
                        .header("User-Agent", System.getProperty("http.agent") + " bmc-okHttp")
                        .build();

                return chain.proceed(request);
            }
        };
    }

    Interceptor appInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain
                    .request();
            Response response = chain.proceed(request);
            return response;
        }
    };

    public class NullOnEmptyConvertFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {

                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) {
                        return null;
                    }
                    return delegate.convert(body);
                }
            };
        }
    }
}
