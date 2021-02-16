package com.mahesh_prajapati.matchingapp.ui.main.view

import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.mahesh_prajapati.cardswapview.*
import com.mahesh_prajapati.matchingapp.coroutines.R
import com.mahesh_prajapati.matchingapp.data.api.ApiHelper
import com.mahesh_prajapati.matchingapp.data.api.RetrofitBuilder
import com.mahesh_prajapati.matchingapp.storage.model.ResultX
import com.mahesh_prajapati.matchingapp.ui.base.ViewModelFactory
import com.mahesh_prajapati.matchingapp.ui.main.adapter.CardStackAdapter
import com.mahesh_prajapati.matchingapp.ui.main.viewmodel.MainViewModel
import com.mahesh_prajapati.matchingapp.utils.SpotDiffCallback
import com.mahesh_prajapati.matchingapp.utils.Status
import kotlinx.android.synthetic.main.fragment_cards.*

class FragmentCards : Fragment(), CardStackListener {

    private lateinit var viewModel: MainViewModel

    private lateinit var manager: CardStackLayoutManager

    private lateinit var adapter: CardStackAdapter

    var list: List<ResultX> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cards, container, false)
        retainInstance = true
        setupViewUi()

        (activity as CardActivity?)!!.setFragmentRefreshListener(object :
            CardActivity.FragmentRefreshListener {
            override fun onRefresh() {
                getDataFromDB()
            }

        })

        return view
    }




    private fun setupViewUi() {
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)))
                .get(MainViewModel::class.java)

        manager = CardStackLayoutManager(activity!!, this)

        adapter = CardStackAdapter(activity!!, list)
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
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        card_stack_view.layoutManager = manager
        card_stack_view.adapter = adapter
        card_stack_view.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
        val old = adapter.getCards()
        val new = old.plus(getDataFromDB())
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setCards(new)
        result.dispatchUpdatesTo(adapter)
    }


    private fun getDataFromDB(): List<ResultX> {
        viewModel.getCardDataFromDB(activity!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { songs ->
                            list = viewModel.filterList(songs)
                        }
                       setUpDataToList()

                    }
                    Status.ERROR -> {
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })

        return list

    }

    private fun setUpDataToList() {
        setupCardStackView()
        setupButton()
        adapter.setCards(list)
        adapter.notifyItemChanged(manager.topPosition)
    }


    private fun setupCardStackView() {
        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.navigation_main_activity, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reload -> {
                (activity as CardActivity).getDataFromApi()
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


    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as CardActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as CardActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as CardActivity).supportActionBar?.title = getString(R.string.app_name)
    }


    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    override fun onCardSwiped(direction: Direction?) {
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
        var result = adapter.getetCurrentItem()
        result!!.selectVal = if (result.selectVal == null || result.selectVal == 0) {
            if (direction == Direction.Left) {
                -1
            } else {
                1
            }
        } else {
            0
        }
        viewModel.updateValueToDB(activity!!, result)
    }

    override fun onCardRewound() {}

    override fun onCardCanceled() {}

    override fun onCardAppeared(view: View?, position: Int) {}

    override fun onCardDisappeared(view: View?, position: Int) {}


}