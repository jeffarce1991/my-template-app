package com.jeff.project420.main.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.project420.Constants
import com.jeff.project420.database.local.Photo
import com.jeff.project420.database.usecase.local.loader.PhotoLocalLoader
import com.jeff.project420.database.usecase.local.saver.PhotoLocalSaver
import com.jeff.project420.webservices.exception.NoInternetException
import com.jeff.project420.webservices.internet.RxInternet
import com.jeff.project420.main.view.MainView
import com.jeff.project420.supplychain.photo.PhotoLoader
import com.jeff.project420.webservices.dto.PhotoDto
import com.jeff.project420.webservices.api.photos.PhotosApi
import com.jeff.project420.webservices.api.RetrofitClientInstance
import com.jeff.project420.utilities.rx.RxSchedulerUtils
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class DefaultMainPresenter @Inject
constructor(
    private val internet: RxInternet,
    private val localLoader: PhotoLocalLoader,
    private val localSaver: PhotoLocalSaver,
    private val schedulerUtils: RxSchedulerUtils,
    private val loader: PhotoLoader
) : MvpBasePresenter<MainView>(),
    MainPresenter {

    lateinit var view: MainView

    lateinit var disposable: Disposable

    private fun getApi(): PhotosApi {

        /*Create handle for the RetrofitInstance interface*/
        return RetrofitClientInstance.getRxRetrofitInstance(
            Constants.Gateways.JSONPLACEHOLDER
        )!!.create(
            PhotosApi::class.java
        )
    }

    override fun getPhotos() {
        internet.isConnected()
            .andThen(loader.loadAll())
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Photo>>{
                override fun onSuccess(t: List<Photo>) {
                    Timber.d("==q onError $t" )
                    view.hideProgress()
                    if (t.isNotEmpty()) {
                        view.generateDataList(t)
                        view.showToast("Data loaded Remotely")
                    } else {
                        view.showLoadingDataFailed()
                    }
                    dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    view.showProgressRemote()
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q onError $e" )
                    e.printStackTrace()

                    view.hideProgress()

                    if (e is NoInternetException) {
                        getPhotosFromLocal()
                    } else {
                        dispose()
                    }
                }
            })
        /*internet.isConnected()
            .andThen(getApi().loadPhotos())
            .flatMapObservable { list -> Observable.fromIterable(list.body()) }
            .flatMap(PhotoDtoToPhotoMapper())
            .toList()
            .flatMap { photos -> Single.fromObservable(localSaver.saveAll(photos)) }
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Photo>> {

                override fun onSubscribe(d: Disposable) {
                    Timber.d("==q onSubscribe")
                    disposable = d

                    view.showProgress()
                }

                override fun onSuccess(t: List<Photo>) {
                    Timber.d("==q onSuccess")

                    view.hideProgress()
                    view.generateDataList(t)

                    dispose()
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q onError $e")

                    view.hideProgress()

                    if (e is NoInternetException) {
                        Timber.d("==q onError is $e")

                        loadAll()
                    } else {

                        dispose()
                    }
                }
            })*/
    }

    override fun getPhoto(id: Int) {
            internet.isConnected()
                .andThen(getApi().loadPhotoById(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response<PhotoDto>> {

                    override fun onSubscribe(d: Disposable) {
                        view.showProgressRemote()

                        disposable = d
                        Timber.d("==q onSubscribe")
                    }

                    override fun onSuccess(t: Response<PhotoDto>) {
                        view.hideProgress()
                        view.generateDataList(mapPhotoDtosToPhotos(listOf(t.body()!!)))

                        dispose()
                        Timber.d("==q onSuccess")
                    }
                    override fun onError(e: Throwable) {
                        view.hideProgress()

                        dispose()
                        Timber.e(e)
                        Timber.d("==q onError")
                        e.printStackTrace()
                    }
                })
    }


    fun getPhotosFromLocal(){
        loader.loadAllFromLocal()
            .compose(schedulerUtils.forSingle())
            .subscribe(object : SingleObserver<List<Photo>>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    view.showProgressLocal()
                }

                override fun onSuccess(t: List<Photo>) {
                    Timber.d("==q loadAll onSuccess ${t.size}")

                    view.hideProgress()

                    if (t.isNotEmpty()) {
                        view.showToast("Data loaded Locally")
                        view.generateDataList(t)
                    } else {
                        view.showLoadingDataFailed()
                    }
                    dispose()
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Load Photos Failed $e")

                    view.hideProgress()
                    dispose()

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

    override fun attachView(view: MainView) {
        super.attachView(view)
        this.view = view
    }

    private fun dispose() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    override fun detachView(retainInstance: Boolean) {
        dispose()
    }

}