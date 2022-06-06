package com.picpay.desafio.android.util

import com.picpay.desafio.android.dto.User


interface ListenerCallback {
    fun itemClicked(objeto: Any)

    fun onSucess(body: List<User>?)
    fun onError()
}