package com.jeff.project420.main

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeff.project420.R
import com.jeff.project420.adapter.CustomAdapter
import com.jeff.project420.database.local.Photo
import com.jeff.project420.databinding.ActivityMainBinding
import com.jeff.project420.main.presenter.MainActivityPresenter


class MainActivity : AppCompatActivity(), MainActivityPresenter.View {
    private lateinit var adapter: CustomAdapter
    private lateinit var progressDialog: ProgressDialog

    private lateinit var presenter: MainActivityPresenter
    lateinit var mainBinding : ActivityMainBinding

    lateinit var photos : List<Photo>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        presenter = MainActivityPresenter(this, this)

        presenter.getPhotoList()
    }


    /*Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*/
    override fun generateDataList(photos: List<Photo>) {
        adapter = CustomAdapter(this, photos)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        mainBinding.customRecyclerView.layoutManager = layoutManager
        mainBinding.customRecyclerView.adapter = adapter
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun showProgress() {
        progressDialog = ProgressDialog.show(
        this,
        "Retrofit",
        "Loading...")
    }
}
