package com.mahesh_prajapati.matchingapp.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mahesh_prajapati.matchingapp.coroutines.R
import com.mahesh_prajapati.matchingapp.data.api.ApiHelper
import com.mahesh_prajapati.matchingapp.data.api.RetrofitBuilder
import com.mahesh_prajapati.matchingapp.ui.base.ViewModelFactory
import com.mahesh_prajapati.matchingapp.ui.main.viewmodel.MainViewModel
import com.mahesh_prajapati.matchingapp.utils.Status
import kotlinx.android.synthetic.main.activity_card.*


class CardActivity : AppCompatActivity(){
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        setupViewUi()
        getDataFromApi()
    }

    private fun setupViewUi() {
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)))
                .get(MainViewModel::class.java)

    }


    fun getDataFromApi() {
        viewModel.getCardDataFromWebService().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { songs ->
                            viewModel.setCardDataToDB(this, songs)
                            progressBar.visibility = View.GONE
                            if(getFragmentRefreshListener()!=null){
                                getFragmentRefreshListener()!!.onRefresh();
                            }
                        }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })

    }

    override fun onBackPressed() {
        if(getFragmentRefreshListener()!=null){
            getFragmentRefreshListener()!!.onRefresh();
        }
        super.onBackPressed()
    }

    public interface FragmentRefreshListener {
        fun onRefresh()
    }

    private var fragmentRefreshListener: FragmentRefreshListener? = null
    fun getFragmentRefreshListener(): FragmentRefreshListener? {
        return fragmentRefreshListener
    }

    fun setFragmentRefreshListener(fragmentRefreshListener: FragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener
    }
}
