package com.picpay.desafio.android.di.module;

import android.app.Application;

import com.picpay.desafio.android.api.PicPayService;
import com.picpay.desafio.android.util.CheckoutConnection;
import com.picpay.desafio.android.util.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModuleJava {

    private final String mBaseUrl;
    private final Application mApplication;

    public ApiModuleJava(String mBaseUrl, Application mApplication) {
        this.mBaseUrl = mBaseUrl;
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
     Cache createCache() {
        return new Cache(mApplication.getCacheDir(), Constants.CACHE_SIZE);
    }

    @Provides
    @Singleton
    CacheControl createCacheControl() {
        return new CacheControl.Builder().maxStale(1, TimeUnit.HOURS).build();
    }

    @Provides
    @Singleton
    OkHttpClient createOkhttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client
                .writeTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .callTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(createCache())
                .addInterceptor(chain -> {
                    Request request = chain.request();

                    if (CheckoutConnection.Companion.isConnected(mApplication)) {
                        request.newBuilder()
                                .header(Constants.HEADER_CACHE_CONTROL, "public, max-age=" + 5)
                                .build();
                    } else {
                        request.newBuilder()
                                .removeHeader(Constants.HEADER_PRAGMA)
                                .removeHeader(Constants.HEADER_CACHE_CONTROL)
                                .cacheControl(createCacheControl())
                                .build();
                    }

                    return chain.proceed(request);
                });

        return client.build();
    }

    @Provides
    @Singleton
     Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(createOkhttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    @Provides
    @Singleton
    PicPayService getPicPayService() {
        return createRetrofit().create(PicPayService.class);
    }

}