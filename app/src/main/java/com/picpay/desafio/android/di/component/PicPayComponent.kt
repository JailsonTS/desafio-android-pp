package com.picpay.desafio.android.di.component

import javax.inject.Singleton
import com.picpay.desafio.android.di.module.AppModule
import com.picpay.desafio.android.di.module.ApiModule
import com.picpay.desafio.android.view.fragments.ContatosListaFragment
import dagger.Component

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface PicPayComponent {
    fun inject(contatosListaFragment: ContatosListaFragment?)
}