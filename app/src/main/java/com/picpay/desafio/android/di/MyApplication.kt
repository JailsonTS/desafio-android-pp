package com.picpay.desafio.android.di

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

//        val mApiComponent = DaggerApplicationComponentJava.builder()
//                .appModule(new AppModule(this))
//                .apiModule(new ApiModule(Constants.PICPAY_CONTATOS))
//                .build();
    }
}