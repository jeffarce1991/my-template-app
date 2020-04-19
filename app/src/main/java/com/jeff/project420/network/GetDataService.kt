package com.jeff.project420.network

import com.jeff.project420.model.PhotoDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface GetDataService {

    @GET("photos")
    fun getAllPhotos(): Single<Response<List<PhotoDto>>>
}