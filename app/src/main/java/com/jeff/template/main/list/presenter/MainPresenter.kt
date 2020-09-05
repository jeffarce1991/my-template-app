package com.jeff.template.main.list.presenter

import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.jeff.template.main.list.view.MainView

interface MainPresenter: MvpPresenter<MainView> {
    fun getPhoto(id: Int)
    fun getPhotos()
}