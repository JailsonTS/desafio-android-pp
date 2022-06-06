package com.picpay.desafio.android.repository

import com.picpay.desafio.android.dto.User
import com.picpay.desafio.android.services.PicPayService
import com.picpay.desafio.android.util.ListenerCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PicPayRepository(private val api: PicPayService, private val listener: ListenerCallback) : PicPayService {

    override suspend fun getUsers() {
        api.getUsers()
            .enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    listener.onError()
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    listener.onSucess(response.body())
                }
            })
    }
}