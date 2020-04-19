package com.jeff.project420.main.presenter

import android.content.Context
import com.jeff.project420.Constants
import com.jeff.project420.database.AppExecutors
import com.jeff.project420.database.local.Photo
import com.jeff.project420.database.room.PhotoDatabase
import com.jeff.project420.model.PhotoDto
import com.jeff.project420.network.GetDataService
import com.jeff.project420.network.RetrofitClientInstance
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class MainActivityPresenter(
    val context: Context,
    var view: View) {

    lateinit var photoDatabase: PhotoDatabase

    fun getPhotoList() {
        /*Create handle for the RetrofitInstance interface*/
        val service =
            RetrofitClientInstance.getRxRetrofitInstance(
                Constants.Gateways.JSONPLACEHOLDER
            )!!.create(
                GetDataService::class.java
            )

        service.getAllPhotos().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<List<PhotoDto>>> {
                override fun onSuccess(t: Response<List<PhotoDto>>) {
                    view.hideProgress()
                    view.generateDataList(mapPhotoDtosToPhotos(t.body()!!))

                    AppExecutors.getInstance().diskIO().execute {
                        photoDatabase = PhotoDatabase.getInstance(context)
                        photoDatabase.photoDao()!!
                            .insertAll(mapPhotoDtosToPhotos(t.body()!!))
                    }
                }
                override fun onSubscribe(d: Disposable) {
                    view.showProgress()
                }
                override fun onError(e: Throwable) {
                    view.hideProgress()
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
