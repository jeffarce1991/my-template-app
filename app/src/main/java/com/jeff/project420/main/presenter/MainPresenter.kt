package com.jeff.project420.main.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.project420.main.view.MainView

interface MainPresenter: MvpPresenter<MainView> {
    fun getPhoto(id: Int)
    fun getPhotos()
}