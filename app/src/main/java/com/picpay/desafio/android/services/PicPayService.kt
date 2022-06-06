package com.picpay.desafio.android.services

import com.picpay.desafio.android.dto.User

import retrofit2.Call
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    suspend fun getUsers(): Call<List<User>>
}