package com.picpay.desafio.android.di.module

import android.content.Context
import com.picpay.desafio.android.enums.Enums
import com.picpay.desafio.android.repository.PicPayRepository
import com.picpay.desafio.android.services.PicPayService
import com.picpay.desafio.android.util.CheckoutConnection
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataModule {

    private val CACHE_CONTROL = CacheControl.Builder().maxStale(1, TimeUnit.HOURS).build()
    private const val CACHE_SIZE = 10 * 1024 * 1024
    private const val TIME_OUT = 12L

    fun load(context: Context) {
        loadKoinModules(postsModule() + networkModule(context))
    }

    private fun postsModule(): Module {
        return module {
            single<PicPayRepository> { PicPayRepositoryImpl(get()) }
        }
    }

    private fun networkModule(context: Context): Module {

        return module {

            val client = createHttpClient(context)

            val retrofit: Retrofit =
                Retrofit.Builder()
                    .baseUrl(Enums.PICPAY_CONTATOS.getString)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()


            createPicPayService(retrofit)
        }

    }

    private fun createHttpClient(context: Context): OkHttpClient {
        val cache = Cache(context.cacheDir, CACHE_SIZE.toLong())

        return OkHttpClient.Builder()
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()

                request = if (CheckoutConnection.isConnected(context)) {
                    request.newBuilder()
                        .header(
                            Enums.HEADER_CACHE_CONTROL.getString,
                            "public, max-age=" + 5
                        )
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

    private fun createPicPayService(retrofit: Retrofit): PicPayService {
        return retrofit.create(PicPayService::class.java)
    }

}