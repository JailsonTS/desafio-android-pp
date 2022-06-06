package com.picpay.desafio.android.util

import com.picpay.desafio.android.data.User

interface CallBack<T, Throwable> {
    fun onSucess(objeto: T)
    fun onError(error: Throwable)
}
