package com.picpay.desafio.android.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public
class ApplicationModuleJava {
    private final Application mApplication;

    public ApplicationModuleJava(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }
}
