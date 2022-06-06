package com.picpay.desafio.android.di.component;

import com.picpay.desafio.android.di.module.ApiModuleJava;
import com.picpay.desafio.android.di.module.ApplicationModuleJava;
import com.picpay.desafio.android.view.fragments.ContatosListaFragmentJava;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModuleJava.class, ApiModuleJava.class})
public interface ApplicationComponentJava {
    void inject(ContatosListaFragmentJava contatosListaFragmentJava);
}