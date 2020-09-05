package com.jeff.template.main.detail.view

import com.hannesdorfmann.mosby.mvp.MvpView

interface DetailsView : MvpView {

    fun stopShimmerAnimations()
    fun startShimmerAnimations()
    fun hideShimmerPlaceholders()
    fun showMessage(message: String)

}
