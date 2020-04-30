package com.jeff.project420.main.view

import com.hannesdorfmann.mosby.mvp.MvpView
import com.jeff.project420.database.local.Photo

interface MainView : MvpView {
     fun hideProgress()
     fun showProgressRemote()
     fun showProgressLocal()

     fun showLoadingDataFailed()
     fun showToast(message: String)
     fun generateDataList(photos: List<Photo>)
}