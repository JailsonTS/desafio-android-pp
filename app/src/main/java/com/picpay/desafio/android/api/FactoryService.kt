package com.picpay.desafio.android.api

import android.content.Context
import com.picpay.desafio.android.util.Constants
import com.picpay.desafio.android.util.CheckoutConnection
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FactoryService(context: Context) {
    //private val url: String = Enums.PICPAY_CONTATOS.getString

    private val CACHE_CONTROL = CacheControl.Builder().maxStale(1, TimeUnit.HOURS).build()
    private val CACHE_SIZE = 10 * 1024 * 1024
    private val TIME_OUT = 12L

    private val cache by lazy {
        Cache(context.cacheDir, CACHE_SIZE.toLong())
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()

                request = if (CheckoutConnection.isConnected(context)) {
                    request.newBuilder().header(Constants.HEADER_CACHE_CONTROL, "public, max-age=" + 5)
                        .build()
                } else {
                    request.newBuilder()
                        .removeHeader(Constants.HEADER_PRAGMA)
                        .removeHeader(Constants.HEADER_CACHE_CONTROL)
                        .cacheControl(CACHE_CONTROL)
                        .build()
                }

                chain.proceed(request)
            }
            .build()
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return   Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getPicPayService(): PicPayService {
        return createRetrofit(Constants.PICPAY_CONTATOS).create(PicPayService::class.java)
    }


}