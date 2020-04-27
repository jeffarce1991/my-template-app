package com.jeff.project420.network

import com.jeff.project420.model.PhotoDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotosApi {

    @GET("photos")
    fun getPhotos(): Single<Response<List<PhotoDto>>>

    @GET("photos/{id}")
    fun getPhotoById(@Path("id") id: Int): Single<Response<PhotoDto>>
}