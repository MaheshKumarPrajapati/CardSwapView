package com.mahesh_prajapati.cardswapview.internal

import android.graphics.Path
import android.view.animation.Interpolator
import com.mahesh_prajapati.cardswapview.Direction


interface AnimationSetting {
    fun getDirection(): Direction
    fun getDuration(): Int
    fun getInterpolator(): Interpolator
}