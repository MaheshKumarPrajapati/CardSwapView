package com.mahesh_prajapati.cardswapview

import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import com.mahesh_prajapati.cardswapview.internal.AnimationSetting

class RewindAnimationSetting(
    private val direction: Direction?,
    private val duration: Int?,
    private val interpolator: Interpolator?
) : AnimationSetting {

    override fun getDirection(): Direction {
        return direction!!
    }

    override fun getDuration(): Int {
        return duration!!
    }

    override fun getInterpolator(): Interpolator {
        return interpolator!!
    }

    class Builder {
        private var direction =
            com.mahesh_prajapati.cardswapview.Direction.Bottom
        private var duration =
            com.mahesh_prajapati.cardswapview.Duration.Normal.duration
        private var interpolator: Interpolator =
            DecelerateInterpolator()

        fun setDirection(direction: com.mahesh_prajapati.cardswapview.Direction): Builder {
            this.direction = direction
            return this
        }

        fun setDuration(duration: Int): Builder {
            this.duration = duration
            return this
        }

        fun setInterpolator(interpolator: Interpolator): Builder {
            this.interpolator = interpolator
            return this
        }

        fun build(): RewindAnimationSetting {
            return RewindAnimationSetting(
                direction,
                duration,
                interpolator
            )
        }
    }

}