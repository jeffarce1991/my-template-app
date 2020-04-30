package com.jeff.project420.webservices.usecase.loader

import com.jeff.project420.webservices.dto.PhotoDto
import io.reactivex.Single

interface PhotoRemoteLoader {

    fun loadAll(): Single<List<PhotoDto>>
}