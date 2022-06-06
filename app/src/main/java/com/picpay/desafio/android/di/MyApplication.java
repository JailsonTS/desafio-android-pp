package com.picpay.desafio.android.di;

import android.app.Application;

import com.picpay.desafio.android.di.component.PicPayComponent;
import com.picpay.desafio.android.di.module.ApiModule;
import com.picpay.desafio.android.di.module.AppModule;
import com.picpay.desafio.android.enums.Enums;

public class MyApplication extends Application {

    private PicPayComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();



//
//        mApiComponent = DaggerPicPayComponent.builder()
//                .appModule(new AppModule(this))
//                .apiModule(new ApiModule(Enums.PICPAY_CONTATOS.getGetString()))
//                .build();
    }

    public PicPayComponent getNetComponent() {
        return mApiComponent;
    }
}