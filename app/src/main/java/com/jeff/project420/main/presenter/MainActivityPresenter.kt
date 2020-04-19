package com.jeff.project420.main.presenter

import android.content.Context
import com.jeff.project420.Constants
import com.jeff.project420.database.AppExecutors
import com.jeff.project420.database.local.Photo
import com.jeff.project420.database.room.PhotoDatabase
import com.jeff.project420.model.PhotoDto
import com.jeff.project420.network.GetDataService
import com.jeff.project420.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class MainActivityPresenter(
    val context: Context,
    var view: View) {

    lateinit var photoDatabase: PhotoDatabase

    fun getPhotoList() {
        view.showProgress()

        /*Create handle for the RetrofitInstance interface*/
        val service =
            RetrofitClientInstance.getRetrofitInstance(
                Constants.Gateways.JSONPLACEHOLDER
            )!!.create(
                GetDataService::class.java
            )

        val call: Call<List<PhotoDto>> = service.allPhotos
        call.enqueue(object : Callback<List<PhotoDto>> {
            override fun onFailure(call: Call<List<PhotoDto>>, t: Throwable) {
                view.hideProgress()
                Timber.e(t)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<PhotoDto>>,
                response: Response<List<PhotoDto>>
            ) {
                view.hideProgress()
                view.generateDataList(mapPhotoDtosToPhotos(response.body()!!))
                AppExecutors.getInstance().diskIO().execute {
                    photoDatabase = PhotoDatabase.getInstance(context)
                    photoDatabase.photoDao()!!
                        .insertAll(mapPhotoDtosToPhotos(response.body()!!))
                }
            }

        })
    }

    private fun mapPhotoDtosToPhotos(photoDtos: List<PhotoDto>): List<Photo> {
        val photos = mutableListOf<Photo>()
        for (photoDto in photoDtos) {
            val photo = Photo(
                photoDto.id,
                photoDto.albumId,
                photoDto.title,
                photoDto.url,
                photoDto.thumbnailUrl
            )
            photos.add(photo)
        }
        return photos
    }


    interface View {
        fun generateDataList(photos: List<Photo>)
        fun hideProgress()
        fun showProgress()
    }
}
