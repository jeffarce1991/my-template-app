package com.jeff.project420.database.usecase.local.saver

import com.jeff.project420.database.local.Photo
import io.reactivex.Completable
import io.reactivex.Observable

interface PhotoLocalSaver {

    fun save(photo: Photo): Completable

    fun saveAll(photos: List<Photo>): Observable<List<Photo>>
}
