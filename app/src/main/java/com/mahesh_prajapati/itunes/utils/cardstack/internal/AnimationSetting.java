package com.mahesh_prajapati.itunes.utils.cardstack.internal;

import android.view.animation.Interpolator;

import com.mahesh_prajapati.itunes.utils.cardstack.Direction;


public interface AnimationSetting {
    Direction getDirection();
    int getDuration();
    Interpolator getInterpolator();
}
