package com.picpay.desafio.android.api

import com.picpay.desafio.dto.User

import retrofit2.Call
import retrofit2.http.GET

interface PicPayService {
    private val URL get()= "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    @GET("users")
    fun getUsers(): Call<List<User>>
}