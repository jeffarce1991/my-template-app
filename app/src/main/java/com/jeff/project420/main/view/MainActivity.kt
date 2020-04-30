package com.jeff.project420.main.view

import android.app.ProgressDialog
import android.app.ProgressDialog.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jeff.project420.R
import com.jeff.project420.adapter.CustomAdapter
import com.jeff.project420.android.base.extension.invokeSimpleDialog
import com.jeff.project420.android.base.extension.longToast
import com.jeff.project420.android.base.extension.shortToast
import com.jeff.project420.database.local.Photo
import com.jeff.project420.databinding.ActivityMainBinding
import com.jeff.project420.main.presenter.MainPresenter
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject


class MainActivity : MvpActivity<MainView, MainPresenter>(), MainView {
    private lateinit var adapter: CustomAdapter
    private lateinit var progressDialog: ProgressDialog

    lateinit var mainBinding : ActivityMainBinding

    lateinit var photos : List<Photo>


    @Inject
    internal lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainPresenter.getPhotos()
    }


    //Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*//*
    override fun generateDataList(photos: List<Photo>) {
        adapter = CustomAdapter(this, photos)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        mainBinding.customRecyclerView.layoutManager = layoutManager
        mainBinding.customRecyclerView.adapter = adapter
    }

    override fun createPresenter(): MainPresenter {
        return mainPresenter
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun showLoadingDataFailed() {
        longToast("Loading data failed")
        /*invokeSimpleDialog("Project420",
            "OK",
            "List is empty or null.")*/
    }

    override fun showToast(message: String) {
        longToast(message)
    }

    override fun showProgressRemote() {
        progressDialog = show(
            this,
            "Project420",
            "Loading data remotely...")
    }

    override fun showProgressLocal() {
        progressDialog = show(
            this,
            "Project420",
            "Loading data locally...")
    }
}
