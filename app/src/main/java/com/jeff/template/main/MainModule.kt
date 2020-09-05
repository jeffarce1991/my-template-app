package com.jeff.template.main

import com.jeff.template.ActivityScope
import com.jeff.template.main.detail.presenter.DetailsPresenterModule
import com.jeff.template.main.detail.view.DetailsActivity
import com.jeff.template.main.list.presenter.MainPresenterModule
import com.jeff.template.main.list.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainPresenterModule::class])
    fun mainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [DetailsPresenterModule::class])
    fun detailsActivity(): DetailsActivity
}