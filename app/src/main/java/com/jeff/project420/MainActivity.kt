package com.jeff.project420

import com.jeff.project420.adapter.CustomAdapter
import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeff.project420.databinding.ActivityMainBinding
import com.jeff.project420.network.RetrofitClientInstance
import com.jeff.project420.model.RetroPhoto
import com.jeff.project420.network.GetDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CustomAdapter
    private lateinit var progressDialog: ProgressDialog

    lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        progressDialog = ProgressDialog.show(
            this,
            "Retrofit",
            "Loading....")

        /*Create handle for the RetrofitInstance interface*/
        val service =
            RetrofitClientInstance.getRetrofitInstance(
                Constants.Gateways.JSONPLACEHOLDER)!!.create(
                GetDataService::class.java
            )

        val call: Call<List<RetroPhoto>> = service.allPhotos
        call.enqueue(object : Callback<List<RetroPhoto>>{
            override fun onFailure(call: Call<List<RetroPhoto>>, t: Throwable) {
                progressDialog.dismiss()
                Timber.e(t)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<RetroPhoto>>,
                response: Response<List<RetroPhoto>>
            ) {
                progressDialog.dismiss()
                generateDataList(response.body()!!);
            }

        })

    }

    /*Method to generate List of data using RecyclerView with custom com.project.retrofit.adapter*/
    private fun generateDataList(photoList: List<RetroPhoto>) {
        adapter = CustomAdapter(this, photoList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        mainBinding.customRecyclerView.layoutManager = layoutManager
        mainBinding.customRecyclerView.adapter = adapter
    }
}
