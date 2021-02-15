package com.mahesh_prajapati.cardswapview.internal

import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.mahesh_prajapati.cardswapview.*

class CardStackSetting {
    var stackFrom = StackFrom.None
    var visibleCount = 3
    var translationInterval = 8.0f
    var scaleInterval = 0.95f // 0.0f - 1.0f
    var swipeThreshold = 0.3f // 0.0f - 1.0f
    var maxDegree = 20.0f
    var directions =
        Direction.HORIZONTAL
    var canScrollHorizontal = true
    var canScrollVertical = true
    var swipeableMethod = SwipeableMethod.AutomaticAndManual
    var swipeAnimationSetting =
        SwipeAnimationSetting.Builder().build()
    var rewindAnimationSetting =
        RewindAnimationSetting.Builder().build()
    var overlayInterpolator: Interpolator = LinearInterpolator()
}