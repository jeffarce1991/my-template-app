package com.jeff.template.main.detail.presenter

import com.jeff.template.main.detail.presenter.DefaultDetailsPresenter
import com.jeff.template.main.detail.presenter.DetailsPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class DetailsPresenterModule {

    @Binds
    abstract fun bindUserDetailsPresenter(
        defaultUserDetailsPresenter: DefaultDetailsPresenter
    ): DetailsPresenter
}