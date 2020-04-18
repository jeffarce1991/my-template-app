package com.jeff.project420.network

import com.jeff.project420.model.PhotoDto
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {

    @get:GET("/photos")
    val allPhotos: Call<List<PhotoDto>>
}