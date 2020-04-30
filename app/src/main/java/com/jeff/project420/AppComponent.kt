package com.jeff.project420

import android.app.Application
import com.jeff.project420.database.DatabaseModule
import com.jeff.project420.webservices.internet.RxInternetModule
import com.jeff.project420.main.MainModule
import com.jeff.project420.supplychain.photo.PhotoUseCaseModule
import com.jeff.project420.utilities.UtilityModule
import com.jeff.project420.webservices.api.ApiFactory
import com.jeff.project420.webservices.api.ApiModule
import com.jeff.project420.webservices.usecase.WebServiceUseCaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [AndroidSupportInjectionModule::class,
    AndroidSupportInjectionModule::class,
    MainModule::class,
    AppModule::class,
    RxInternetModule::class,
    UtilityModule::class,
    DatabaseModule::class,
    ApiModule::class,
    WebServiceUseCaseModule::class,
    PhotoUseCaseModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(myApplication: MyApplication)
}