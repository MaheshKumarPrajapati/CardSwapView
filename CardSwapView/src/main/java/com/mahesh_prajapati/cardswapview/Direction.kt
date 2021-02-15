package com.mahesh_prajapati.cardswapview

import java.util.*

enum class Direction {
    Left, Right, Top, Bottom;

    companion object {
        val HORIZONTAL =
            Arrays.asList(
                Left,
                Right
            )
        val VERTICAL =
            Arrays.asList(
                Top,
                Bottom
            )
        val FREEDOM =
            Arrays.asList(*values())
    }
}