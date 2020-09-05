package com.jeff.template.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.jakewharton.picasso.OkHttp3Downloader
import com.jeff.template.R
import com.jeff.template.adapter.CustomAdapter.CustomViewHolder
import com.jeff.template.database.local.Photo
import com.jeff.template.databinding.CustomRowBinding
import com.jeff.template.main.detail.view.DetailsActivity
import com.squareup.picasso.Picasso

internal class CustomAdapter(
    private val context: Context,
    private val dataList: List<Photo>
) : RecyclerView.Adapter<CustomViewHolder>() {

    internal inner class CustomViewHolder(binding: CustomRowBinding) :
        ViewHolder(binding.root) {
        var itemLayout: ConstraintLayout = binding.itemLayout
        var txtTitle: TextView = binding.customRowTitle
        val thumbnail: ImageView = binding.thumbnail

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<CustomRowBinding>(LayoutInflater.from(p0.context),
            R.layout.custom_row,
            p0,
            false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = dataList[position]
        holder.txtTitle.text = item.title

        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(item.thumbnailUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.thumbnail)

        /*
        There was 1 cause:
        java.io.FileNotFoundException(https://via.placeholder.com/600/92c952)
        only on https://via.placeholder.com/

        Glide
            .with(context)
            .load(item.url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .fallback(R.drawable.ic_launcher_background)
            .into(holder.thumbnail)*/

        holder.itemLayout.setOnClickListener {
            val intent = DetailsActivity.getStartIntent(
                context,
                item.id,
                item.title,
                item.url,
                item.thumbnailUrl
            )
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}