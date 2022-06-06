package com.picpay.desafio.android.di.component

import com.picpay.desafio.android.di.module.ApiModule
import com.picpay.desafio.android.di.module.ApplicationModule
import javax.inject.Singleton
import com.picpay.desafio.android.view.fragments.ContatosListaFragment
import dagger.Component

@Singleton
@Component(modules = [ApiModule::class, ApplicationModule::class])
interface ApplicationComponent {
   fun inject(contatosListaFragment: ContatosListaFragment)
}