package com.jeff.template.main.detail.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jakewharton.picasso.OkHttp3Downloader
import com.jeff.template.R
import com.jeff.template.android.base.extension.hide
import com.jeff.template.databinding.ActivityDetailsBinding
import com.jeff.template.main.detail.presenter.DetailsPresenter
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_details.view.*
import javax.inject.Inject


class DetailsActivity : MvpActivity<DetailsView, DetailsPresenter>(),
    DetailsView {

    companion object {
        private var EXTRA_ID = "EXTRA_ID"
        private var EXTRA_TITLE = "EXTRA_TITLE"
        private var EXTRA_URL = "EXTRA_URL"
        private var EXTRA_THUMBNAIL_URL = "EXTRA_THUMBNAIL_URL"

        fun getStartIntent(
            context: Context,
            id : Int,
            title : String,
            url : String,
            thumbnailUrl : String


        ): Intent {
            return Intent(context, DetailsActivity::class.java)
                .putExtra(EXTRA_ID, id)
                .putExtra(EXTRA_TITLE, title)
                .putExtra(EXTRA_URL, url)
                .putExtra(EXTRA_THUMBNAIL_URL, thumbnailUrl)
        }
    }

    @Inject
    internal lateinit var detailsPresenter : DetailsPresenter

    private lateinit var binding : ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        setupToolbar()
        startShimmerAnimations()
        setDetails(getUrl()!!, getTittle()!!)
        //userDetailsPresenter.loadUserDetails(getUserName()!!, getId()!!)
        //userDetailsPresenter.loadNotes(getId()!!)
        /*binding.root.save_notes.setOnClickListener {
            userDetailsPresenter.updateNotes(
                binding.root.notes.text.toString(),
                getId()!!
            )
        }*/
    }

    private fun getUrl(): String? = intent.getStringExtra(EXTRA_URL)
    private fun getThumnailUrl(): String? = intent.getStringExtra(EXTRA_THUMBNAIL_URL)
    private fun getTittle(): String? = intent.getStringExtra(EXTRA_TITLE)
    private fun getId(): Int? = intent.getIntExtra(EXTRA_ID, -1)

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar!!.title = intent.getStringExtra(resources.getString(R.string.app_name))
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun createPresenter(): DetailsPresenter {
        return detailsPresenter
    }

    private fun setDetails(url: String, title: String) {
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this))
        builder.build().load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(binding.headerImage)

        binding.root.name.text = title

        stopShimmerAnimations()
        hideShimmerPlaceholders()

        /*Glide
            .with(this)
            .load(url)
            .centerCrop()
            .placeholder(ColorDrawable(resources.getColor(R.color.colorPrimary)))
            .into(binding.headerImage)*/
    }

    /*override fun setNotes(notes: Notes) {
        binding.root.notes.setText(notes.content)
    }*/

    override fun showMessage(message: String) {
        Snackbar.make(binding.coordLayout,
            message,
            Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun startShimmerAnimations() {
        binding.root.shimmer_details_container.startShimmerAnimation()
        binding.root.shimmer_follows_container.startShimmerAnimation()
    }

    override fun hideShimmerPlaceholders() {
        binding.root.name_shimmer.hide()
    }

    override fun stopShimmerAnimations() {
        binding.root.shimmer_details_container.stopShimmerAnimation();
        binding.root.shimmer_follows_container.stopShimmerAnimation();
    }
}