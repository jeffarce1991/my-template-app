package com.jeff.project420.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jakewharton.picasso.OkHttp3Downloader
import com.jeff.project420.R
import com.jeff.project420.adapter.CustomAdapter.CustomViewHolder
import com.jeff.project420.model.RetroPhoto
import com.squareup.picasso.Picasso

internal class CustomAdapter(
    private val context: Context,
    private val dataList: List<RetroPhoto>
) : RecyclerView.Adapter<CustomViewHolder>() {

    internal inner class CustomViewHolder(mView: View) :
        ViewHolder(mView) {
        var txtTitle: TextView = mView.findViewById(R.id.customRowTitle)
        val coverImage: ImageView = mView.findViewById(R.id.coverImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.custom_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.txtTitle.text = dataList[position].title
        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(dataList[position].thumbnailUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.coverImage)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}