package com.jeff.template.main.detail.presenter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.jeff.template.main.detail.view.DetailsView
import com.jeff.template.main.detail.presenter.DetailsPresenter
import com.jeff.template.utilities.rx.RxSchedulerUtils
import com.jeff.template.webservices.internet.RxInternet
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class DefaultDetailsPresenter @Inject
constructor(
    private val rxInternet: RxInternet,
    private val rxScheduler: RxSchedulerUtils
) : MvpBasePresenter<DetailsView>(),
    DetailsPresenter {

    lateinit var view: DetailsView

    lateinit var disposable: Disposable

    override fun attachView(view: DetailsView) {
        super.attachView(view)
        this.view = view
    }

    private fun dispose() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    override fun detachView(retainInstance: Boolean) {
        //dispose()
    }
}