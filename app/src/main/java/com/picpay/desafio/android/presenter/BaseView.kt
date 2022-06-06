package com.picpay.desafio.android.presenter

interface BaseView<T> {
    var presenter : T
    fun bindViews()
}

