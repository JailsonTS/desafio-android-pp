package com.picpay.desafio.android.api

import com.picpay.desafio.enums.enums
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BuildService {
    private val url: String = enums.PICPAY_CONTATOS.url
    private val TIMEOUT = 15L

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            //.addInterceptor(interceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getPicPayService(): PicPayService {
        return retrofit.create(PicPayService::class.java)
    }





}