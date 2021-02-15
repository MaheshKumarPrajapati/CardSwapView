package com.mahesh_prajapati.itunes.ui.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mahesh_prajapati.itunes.coroutines.R
import com.mahesh_prajapati.itunes.data.api.ApiHelper
import com.mahesh_prajapati.itunes.data.api.RetrofitBuilder
import com.mahesh_prajapati.itunes.storage.model.ResultX
import com.mahesh_prajapati.itunes.ui.base.ViewModelFactory
import com.mahesh_prajapati.itunes.ui.main.viewmodel.MainViewModel
import com.mahesh_prajapati.itunes.utils.Status
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*

class CardDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    var data: ResultX? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        setUpData()
    }

    private fun setUpData() {
        setupViewModel()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getSongDetails(intent).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { songs -> SetUpUI(songs = songs) }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun SetUpUI(songs: ResultX) {
        data = songs

        tvSongName.text = getString(R.string.song_name) + " " +  "${songs.name!!.title} ${songs.name.first} ${songs.name.last} "
        tvSongArtist.text = getString(R.string.artist) + " " + songs.dob!!.age
        tvCollectionName.text =
            getString(R.string.collection) + " " + songs.location!!.city
        Glide.with(ivSongPreview.context)
            .load(songs.picture!!.large)
            .into(ivSongPreview)




    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }


    override fun onStop() {
        super.onStop()
    }


    override fun onBackPressed() {
        finish()
    }

}