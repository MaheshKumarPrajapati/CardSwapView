package com.mahesh_prajapati.cardswapview.internal

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.mahesh_prajapati.cardswapview.CardStackLayoutManager

class CardStackDataObserver(private val recyclerView: RecyclerView) : AdapterDataObserver() {
    override fun onChanged() {
        val manager = cardStackLayoutManager
        manager.topPosition = 0
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        // Do nothing
    }

    override fun onItemRangeChanged(
        positionStart: Int,
        itemCount: Int,
        payload: Any?
    ) {
        // Do nothing
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        // Do nothing
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        // 要素が削除された場合はTopPositionの調整が必要になる場合がある
        // 具体的には、要素が全て削除された場合と、TopPositionより前の要素が削除された場合は調整が必要
        val manager = cardStackLayoutManager
        val topPosition = manager.topPosition
        if (manager.itemCount == 0) {
            // 要素が全て削除された場合
            manager.topPosition = 0
        } else if (positionStart < topPosition) {
            // TopPositionよりも前の要素が削除された場合
            val diff = topPosition - positionStart
            manager.topPosition = Math.min(topPosition - diff, manager.itemCount - 1)
        }
    }

    override fun onItemRangeMoved(
        fromPosition: Int,
        toPosition: Int,
        itemCount: Int
    ) {
        val manager = cardStackLayoutManager
        manager.removeAllViews()
    }

    private val cardStackLayoutManager: CardStackLayoutManager
        private get() {
            val manager = recyclerView.layoutManager
            if (manager is CardStackLayoutManager) {
                return manager
            }
            throw IllegalStateException("CardStackView must be set CardStackLayoutManager.")
        }

}