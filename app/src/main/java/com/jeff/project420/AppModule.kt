package com.jeff.project420

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun getContext(application: Application): Context {
        return application
    }
}