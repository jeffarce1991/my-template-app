package com.jeff.project420.supplychain.photo

import com.jeff.project420.database.local.Photo
import com.jeff.project420.webservices.dto.PhotoDto
import io.reactivex.Single

interface PhotoLoader {

    fun loadAll(): Single<List<Photo>>

    fun loadAllFromLocal(): Single<List<Photo>>
}