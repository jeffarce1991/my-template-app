package com.jeff.project420.webservices.usecase

import com.jeff.project420.webservices.usecase.loader.DefaultPhotoRemoteLoader
import com.jeff.project420.webservices.usecase.loader.PhotoRemoteLoader
import dagger.Binds
import dagger.Module

@Module
interface WebServiceUseCaseModule {

    @Binds
    fun bindPhotoRemoteLoader(
            defaultPhotoRemoteLoader: DefaultPhotoRemoteLoader):
            PhotoRemoteLoader
}