package com.jeff.template.main.list.presenter

import dagger.Binds
import dagger.Module

@Module
abstract class MainPresenterModule {

    @Binds
    abstract fun bindMainPresenter(
        defaultSplashPresenter: DefaultMainPresenter
    ): MainPresenter
}