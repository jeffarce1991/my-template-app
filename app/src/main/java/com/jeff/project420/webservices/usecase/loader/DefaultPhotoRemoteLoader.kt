package com.jeff.project420.webservices.usecase.loader

import com.jeff.project420.webservices.api.ApiFactory
import com.jeff.project420.webservices.api.photos.PhotosApi
import com.jeff.project420.webservices.dto.PhotoDto
import com.jeff.project420.webservices.transformer.NullResultTransformer
import com.jeff.project420.webservices.transformer.ResponseCodeNot200SingleTransformer
import com.jeff.project420.webservices.transformer.SessionExpiredSingleTransformer
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class DefaultPhotoRemoteLoader @Inject
constructor(private val apiFactory: ApiFactory): PhotoRemoteLoader {

    override fun loadAll(): Single<List<PhotoDto>> {
        return apiFactory.create(PhotosApi::class.java)
            .flatMap { it.loadPhotos() }
            .compose(ResponseCodeNot200SingleTransformer())
            .flatMap { response ->
                Single.just(response.body()!!) }
    }
}