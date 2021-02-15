package com.mahesh_prajapati.cardswapview.internal

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.mahesh_prajapati.cardswapview.CardStackLayoutManager
import com.mahesh_prajapati.cardswapview.Duration
import com.mahesh_prajapati.cardswapview.SwipeAnimationSetting

class CardStackSnapHelper : SnapHelper() {
    private var velocityX = 0
    private var velocityY = 0
    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        if (layoutManager is CardStackLayoutManager) {
            val manager = layoutManager
            if (manager.findViewByPosition(manager.topPosition) != null) {
                val x = targetView.translationX.toInt()
                val y = targetView.translationY.toInt()
                if (x != 0 || y != 0) {
                    val setting = manager.cardStackSetting
                    val horizontal =
                        Math.abs(x) / targetView.width.toFloat()
                    val vertical =
                        Math.abs(y) / targetView.height.toFloat()
                    val duration =
                        Duration.fromVelocity(if (velocityY < velocityX) velocityX else velocityY)
                    if (duration === Duration.Fast || setting.swipeThreshold < horizontal || setting.swipeThreshold < vertical) {
                        val state = manager.cardStackState
                        if (setting.directions.contains(state.direction)) {
                            state.targetPosition = state.topPosition + 1
                            val swipeAnimationSetting =
                                SwipeAnimationSetting.Builder()
                                    .setDirection(setting.swipeAnimationSetting.getDirection()!!)
                                    .setDuration(duration.duration)
                                    .setInterpolator(setting.swipeAnimationSetting.getInterpolator())
                                    .build()
                            manager.setSwipeAnimationSetting(swipeAnimationSetting)
                            velocityX = 0
                            velocityY = 0
                            val scroller = CardStackSmoothScroller(
                                CardStackSmoothScroller.ScrollType.ManualSwipe,
                                manager
                            )
                            scroller.targetPosition = manager.topPosition
                            manager.startSmoothScroll(scroller)
                        } else {
                            val scroller = CardStackSmoothScroller(
                                CardStackSmoothScroller.ScrollType.ManualCancel,
                                manager
                            )
                            scroller.targetPosition = manager.topPosition
                            manager.startSmoothScroll(scroller)
                        }
                    } else {
                        val scroller = CardStackSmoothScroller(
                            CardStackSmoothScroller.ScrollType.ManualCancel,
                            manager
                        )
                        scroller.targetPosition = manager.topPosition
                        manager.startSmoothScroll(scroller)
                    }
                }
            }
        }
        return IntArray(2)
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        if (layoutManager is CardStackLayoutManager) {
            val manager = layoutManager
            val view = manager.findViewByPosition(manager.topPosition)
            if (view != null) {
                val x = view.translationX.toInt()
                val y = view.translationY.toInt()
                return if (x == 0 && y == 0) {
                    null
                } else view
            }
        }
        return null
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        this.velocityX = Math.abs(velocityX)
        this.velocityY = Math.abs(velocityY)
        if (layoutManager is CardStackLayoutManager) {
            return layoutManager.topPosition
        }
        return RecyclerView.NO_POSITION
    }
}