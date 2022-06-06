package com.picpay.desafio.android.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val mApplication: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication
    }
}