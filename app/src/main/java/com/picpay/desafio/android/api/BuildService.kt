package com.picpay.desafio.android.api

import android.content.Context
import com.picpay.desafio.android.enums.Enums
import com.picpay.desafio.android.util.CheckoutConnection
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BuildService(context: Context) {
    private val url: String = Enums.PICPAY_CONTATOS.getString

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
                    request.newBuilder().header(Enums.HEADER_CACHE_CONTROL.getString, "public, max-age=" + 5)
                        .build()
                } else {
                    request.newBuilder()
                        .removeHeader(Enums.HEADER_PRAGMA.getString)
                        .removeHeader(Enums.HEADER_CACHE_CONTROL.getString)
                        .cacheControl(CACHE_CONTROL)
                        .build()
                }

                chain.proceed(request)
            }
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