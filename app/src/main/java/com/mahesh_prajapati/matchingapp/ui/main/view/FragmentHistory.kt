package com.mahesh_prajapati.matchingapp.ui.main.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahesh_prajapati.matchingapp.coroutines.R
import com.mahesh_prajapati.matchingapp.data.api.ApiHelper
import com.mahesh_prajapati.matchingapp.data.api.RetrofitBuilder
import com.mahesh_prajapati.matchingapp.storage.model.ResultX
import com.mahesh_prajapati.matchingapp.ui.base.ViewModelFactory
import com.mahesh_prajapati.matchingapp.ui.main.adapter.HistoryListAdaptor
import com.mahesh_prajapati.matchingapp.ui.main.viewmodel.MainViewModel
import com.mahesh_prajapati.matchingapp.utils.AppConstants
import com.mahesh_prajapati.matchingapp.utils.Status
import kotlinx.android.synthetic.main.fragment_history.*


class FragmentHistory: Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: HistoryListAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setupViewModel()
        setUpUI()
        showData(AppConstants.All_DATA)
    }

    private fun setUpToolbar() {
        (activity as CardActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as CardActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as CardActivity).supportActionBar?.title=getString(R.string.all)
    }

    private fun setUpUI() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = HistoryListAdaptor(arrayListOf(), activity!!)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.history_page_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.getItemId()) {
            R.id.menu_all -> {
                (activity as CardActivity).supportActionBar?.title=getString(R.string.all)
                showData(AppConstants.All_DATA)
                true
            }
            R.id.menu_selected -> {
                (activity as CardActivity).supportActionBar?.title=getString(R.string.selected)
                showData(AppConstants.SELECTED_DATA)
                true
            }

            R.id.menu_rejected -> {
                (activity as CardActivity).supportActionBar?.title=getString(R.string.rejected)
                showData(AppConstants.REJECTED_DATA)
                true
            }
            android.R.id.home -> {
                (activity as CardActivity).onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showData(type: String) {
            viewModel.getHistoryWithFilteredData(type,activity ).observe(activity!!, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            recyclerView.visibility = View.VISIBLE
                            resource.data?.let { songs -> retrieveList(songs = songs) }
                        }
                        Status.ERROR -> {
                            recyclerView.visibility = View.VISIBLE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            recyclerView.visibility = View.GONE
                        }
                    }
                }
            })
    }

    private fun retrieveList(songs: List<ResultX>) {
        adapter.apply {
            addCards(songs)
            notifyDataSetChanged()
        }
    }
}