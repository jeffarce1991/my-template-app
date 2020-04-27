package com.jeff.project420.main

import com.jeff.project420.ActivityScope
import com.jeff.project420.main.presenter.MainPresenterModule
import com.jeff.project420.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainPresenterModule::class])
    fun mainActivity(): MainActivity
}