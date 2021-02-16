package com.mahesh_prajapati.matchingapp.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mahesh_prajapati.matchingapp.coroutines.R
import com.mahesh_prajapati.matchingapp.data.api.ApiHelper
import com.mahesh_prajapati.matchingapp.data.api.RetrofitBuilder
import com.mahesh_prajapati.matchingapp.storage.model.ResultX
import com.mahesh_prajapati.matchingapp.ui.base.ViewModelFactory
import com.mahesh_prajapati.matchingapp.ui.main.viewmodel.MainViewModel
import com.mahesh_prajapati.matchingapp.utils.HelperMethods
import com.mahesh_prajapati.matchingapp.utils.Status
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*


class CardDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        Mapbox.getInstance(this, getString(R.string.map_box_key))
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
        viewModel.getCardDetails(intent).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { card -> SetUpUI(card = card) }
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

    private fun SetUpUI(card: ResultX) {

        tvPersonGender.text = buildSpannedString {
            bold { append("${getString(R.string.person_gender)}\n") }
            append("${card.gender}")
        }

        tvPersonAge.text = buildSpannedString {
            bold { append("${getString(R.string.person_age)}\n") }
            append("${HelperMethods().parseDate(card.dob!!.date!!)} ( ${card.dob!!.age} ${"Years"} )")
        }

        val address="${card.location!!.street.number} ${card.location!!.street.name} ${card.location.city} ${card.location.state} ${card.location.country} ${card.location.postcode}"
        tvPersonLocation.text = buildSpannedString {
            bold { append("${getString(R.string.complete_address)}\n") }
            append(address)
        }

        tvPersonEmail.text = buildSpannedString {
            bold { append("${getString(R.string.person_email)}\n") }
            append("${card.email}")
        }

        tvPersonPhone.text = buildSpannedString {
            bold { append("${getString(R.string.person_phone)}\n") }
            append("${card.phone}, ${card.cell}")
        }

        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title =
            "${card.name!!.title} ${card.name.first} ${card.name.last}"

        Glide.with(ivCardImage.context)
            .load(card.picture!!.large)
            .into(ivCardImage)

        if (card.location!!.coordinates.latitude.isNullOrEmpty() || card.location!!.coordinates.longitude.isNullOrEmpty()) {
            container.visibility = View.GONE
        } else {
            setUpMapBox(card.location!!.coordinates.latitude, card.location!!.coordinates.longitude,address)

        }

    }

    private fun setUpMapBox(
        latitude: String?,
        longitude: String?,
        address: String
    ) {
        val symbolLayerIconFeatureList: MutableList<Feature> = ArrayList()
        symbolLayerIconFeatureList.add(
            Feature.fromGeometry(
                Point.fromLngLat((latitude ?: "0.0").toDouble(), (longitude ?: "0.0").toDouble())
            )
        )

        val mapFragment: SupportMapFragment

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        val options = MapboxMapOptions.createFromAttributes(this, null)
        options.camera(
            CameraPosition.Builder()
                .target(LatLng((latitude ?: "0.0").toDouble(), (longitude ?: "0.0").toDouble()))
                .zoom(13.0)
                .build()
        )

        mapFragment = SupportMapFragment.newInstance(options)

        transaction.add(R.id.container, mapFragment, "com.mapbox.map")
        transaction.commit()

        mapFragment!!.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) { it ->
                mapboxMap.addMarker(MarkerOptions().position(LatLng((latitude ?: "0.0").toDouble(), (longitude ?: "0.0").toDouble())).setTitle(address))
            }
        }
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