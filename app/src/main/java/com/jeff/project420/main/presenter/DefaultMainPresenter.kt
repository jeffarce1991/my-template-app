package com.jeff.project420.main.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.project420.Constants
import com.jeff.project420.database.local.Photo
import com.jeff.project420.database.usecase.local.loader.PhotoLocalLoader
import com.jeff.project420.database.usecase.local.saver.PhotoLocalSaver
import com.jeff.project420.exception.NoInternetException
import com.jeff.project420.internet.RxInternet
import com.jeff.project420.main.mapper.PhotoDtoToPhotoMapper
import com.jeff.project420.main.view.MainView
import com.jeff.project420.model.PhotoDto
import com.jeff.project420.network.PhotosApi
import com.jeff.project420.network.RetrofitClientInstance
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
    private val loader: PhotoLocalLoader,
    private val saver: PhotoLocalSaver,
    private val schedulerUtils: RxSchedulerUtils
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
            .andThen(getApi().getPhotos())
            .flatMapObservable { list -> Observable.fromIterable(list.body()) }
            .flatMap(PhotoDtoToPhotoMapper())
            .toList()
            .flatMap { photos -> Single.fromObservable(saver.saveAll(photos)) }
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
            })
    }

    override fun getPhoto(id: Int) {
            internet.isConnected()
                .andThen(getApi().getPhotoById(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response<PhotoDto>> {

                    override fun onSubscribe(d: Disposable) {
                        view.showProgress()

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


    fun loadAll(){
        loader.all()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Photo>>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    view.showProgress()
                }

                override fun onSuccess(t: List<Photo>) {
                    Timber.d("==q loadAll onSuccess ${t.size}")

                    view.hideProgress()
                    view.generateDataList(t)
                    dispose()
                }

                override fun onError(e: Throwable) {
                    Timber.d("==q Load Photos Failed \n$e")

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