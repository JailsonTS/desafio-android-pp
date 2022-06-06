package com.picpay.desafio.android.di;

import android.app.Application;

import com.picpay.desafio.android.di.component.ApplicationComponent;
import com.picpay.desafio.android.di.component.DaggerApplicationComponentJava;
import com.picpay.desafio.android.di.module.ApplicationModuleJava;
import com.picpay.desafio.android.di.module.ApiModuleJava;
import com.picpay.desafio.android.util.Constants;

public class MyApplicationJava extends Application {

    private ApplicationComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiComponent = (ApplicationComponent) DaggerApplicationComponentJava.builder()
                .applicationModuleJava(new ApplicationModuleJava(this))
                .apiModuleJava(new ApiModuleJava(Constants.PICPAY_CONTATOS, this))
                .build();
    }

    public ApplicationComponent getNetComponent() {
        return mApiComponent;
    }
}