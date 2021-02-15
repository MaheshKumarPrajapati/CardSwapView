package com.mahesh_prajapati.itunes.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.mahesh_prajapati.cardswapview.*
import com.mahesh_prajapati.itunes.coroutines.R
import com.mahesh_prajapati.itunes.data.api.ApiHelper
import com.mahesh_prajapati.itunes.data.api.RetrofitBuilder
import com.mahesh_prajapati.itunes.storage.model.ResultX
import com.mahesh_prajapati.itunes.ui.base.ViewModelFactory
import com.mahesh_prajapati.itunes.ui.main.adapter.CardStackAdapter
import com.mahesh_prajapati.itunes.ui.main.viewmodel.MainViewModel
import com.mahesh_prajapati.itunes.utils.MyApp
import com.mahesh_prajapati.itunes.utils.SpotDiffCallback
import com.mahesh_prajapati.itunes.utils.Status
import kotlinx.android.synthetic.main.fragment_cards.*
import java.lang.Exception

class FragmentCards: Fragment(),
    com.mahesh_prajapati.cardswapview.CardStackListener {
    private lateinit var viewModel: MainViewModel

    private val manager by lazy {
        CardStackLayoutManager(
            activity!!,
            this
        )
    }
    var list:List<ResultX> =ArrayList()
    private val adapter by lazy { CardStackAdapter(activity!!,list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_cards, container, false)
        retainInstance=true
        setupViewModel()
        if(MyApp.isFirstTime){
            createSpots()
        }else{
            getDataFromDB()
        }
        return view
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }


    private fun setupButton() {
        skip_button.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            card_stack_view.swipe()
        }

        rewind_button.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager.setRewindAnimationSetting(setting)
            card_stack_view.rewind()
        }


        like_button.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            card_stack_view.swipe()
        }
    }

    private fun initialize() {
        try{
            manager.setStackFrom(com.mahesh_prajapati.cardswapview.StackFrom.None)
            manager.setVisibleCount(3)
            manager.setTranslationInterval(8.0f)
            manager.setScaleInterval(0.95f)
            manager.setSwipeThreshold(0.3f)
            manager.setMaxDegree(20.0f)
            manager.setDirections(com.mahesh_prajapati.cardswapview.Direction.HORIZONTAL)
            manager.setCanScrollHorizontal(true)
            manager.setCanScrollVertical(true)
            manager.setSwipeableMethod(com.mahesh_prajapati.cardswapview.SwipeableMethod.AutomaticAndManual)
            manager.setOverlayInterpolator(LinearInterpolator())
            card_stack_view.layoutManager = manager
            card_stack_view.adapter = adapter
            card_stack_view.itemAnimator.apply {
                if (this is DefaultItemAnimator) {
                    supportsChangeAnimations = false
                }
            }
        }catch (e:Exception){

        }

    }

    private fun paginate() {
        val old = adapter.getSpots()
        val new = old.plus(getDataFromDB())
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun createSpots() {

        viewModel.getSongList() .observe(activity!!, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        //  recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        MyApp.isFirstTime=false
                        resource.data?.let { songs ->
                            viewModel.setCondidatesToDataBase(activity!!,songs)
                        }
                        getDataFromDB()
                    }
                    Status.ERROR -> {
                        // recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        getDataFromDB()
                        Toast.makeText(activity!!, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        // recyclerView.visibility = View.GONE
                    }
                }
            }
        })

    }

    private fun getDataFromDB(): List<ResultX> {
        viewModel.getSongFromHistory(activity!!).observe(activity!!, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        //  recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { songs ->
                            list=viewModel.filterList(songs)

                        }
                        setupCardStackView()
                        setupButton()
                        adapter.setSpots(list)
                        adapter.notifyItemChanged(manager.topPosition)

                    }
                    Status.ERROR -> {
                        // recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        // recyclerView.visibility = View.GONE
                    }
                }
            }
        })

        return list

    }


    private fun setupCardStackView() {
        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.navigation_main_activity, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.getItemId()) {
            R.id.reload -> {
                reload()
                true
            }
            R.id.history -> {
                openHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openHistory() {
        findNavController().navigate(R.id.action_FragmentCards_to_FragmentHistory)
    }

    private fun reload() {
        createSpots()
        /* val old = adapter.getSpots()
         val new = getDataFromDB()
         val callback = SpotDiffCallback(old, new)
         val result = DiffUtil.calculateDiff(callback)
         adapter.setSpots(new)
         result.dispatchUpdatesTo(adapter)*/
    }


    override fun onCardDragging(direction: com.mahesh_prajapati.cardswapview.Direction?, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction!!.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: com.mahesh_prajapati.cardswapview.Direction?) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
        var result=adapter.getetCurrentItem()
        result!!.selectVal=if(result.selectVal==null || result.selectVal==0){if(direction== com.mahesh_prajapati.cardswapview.Direction.Left){-1}else{1}}else{0}
        viewModel.updateValueToDB(activity!!,result)
    }



    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        val textView = view!!.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        val textView = view!!.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")

    }


}