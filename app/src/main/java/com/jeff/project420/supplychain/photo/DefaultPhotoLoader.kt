package com.jeff.project420.supplychain.photo

import com.jeff.project420.database.local.Photo
import com.jeff.project420.database.usecase.local.loader.PhotoLocalLoader
import com.jeff.project420.database.usecase.local.saver.PhotoLocalSaver
import com.jeff.project420.main.mapper.PhotoDtoToPhotoMapper
import com.jeff.project420.webservices.dto.PhotoDto
import com.jeff.project420.webservices.internet.DefaultRxInternet
import com.jeff.project420.webservices.internet.RxInternet
import com.jeff.project420.webservices.usecase.loader.PhotoRemoteLoader
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DefaultPhotoLoader @Inject
constructor(private val remoteLoader: PhotoRemoteLoader,
            private val localLoader: PhotoLocalLoader,
            private val localSaver: PhotoLocalSaver,
            private val rxInternet: RxInternet): PhotoLoader{

    override fun loadAll(): Single<List<Photo>> {
        return rxInternet.isConnected()
            .andThen(remoteLoader.loadAll())
            .flatMapObservable { list -> Observable.fromIterable(list) }
            .flatMap(PhotoDtoToPhotoMapper())
            .toList()
            .flatMap { photos -> Single.fromObservable(localSaver.saveAll(photos)) }
            .flatMap { photos -> Single.just(photos) }

        //return session.activeAdministeredAccount()
        //.map { administeredAccount -> administeredAccount.tenantId }
        //.flatMap { tenantId -> remoteLoader.loadAvailableExpenses(tenantId) }
    }

}