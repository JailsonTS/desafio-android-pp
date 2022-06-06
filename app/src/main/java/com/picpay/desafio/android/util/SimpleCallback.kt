package com.picpay.desafio.android.util

interface SimpleCallback<T> {
    fun onComplete(result: T)
    fun onError()

}