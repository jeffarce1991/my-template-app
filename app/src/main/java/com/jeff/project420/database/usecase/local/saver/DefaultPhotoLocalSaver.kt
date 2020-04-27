package com.jeff.project420.database.usecase.local.saver

import com.jeff.project420.database.local.Photo
import com.jeff.project420.database.room.dao.PhotoDao
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class DefaultPhotoLocalSaver @Inject
constructor(private val dao: PhotoDao) : PhotoLocalSaver {

    override fun save(photo: Photo): Completable {
        return Completable.fromAction { dao.insert(photo)}
    }

    override fun saveAll(photos: List<Photo>): Observable<List<Photo>> {
        return Completable.fromCallable {
            dao.insert(photos)
            Timber.d("==q saveAll Done")
            Completable.complete()
        }.andThen(Observable.fromCallable { photos })
    }

}