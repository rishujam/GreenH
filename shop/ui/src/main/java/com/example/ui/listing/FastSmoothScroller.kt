package com.example.ui.listing

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

/*
 * Created by Sudhanshu Kumar on 05/12/24.
 */

class FastSmoothScroller(context: Context?) : LinearSmoothScroller(context) {
    override fun calculateTimeForDeceleration(dx: Int): Int {
        return super.calculateTimeForDeceleration(dx) / 2
    }

    override fun calculateTimeForScrolling(dx: Int): Int {
        return super.calculateTimeForScrolling(dx) / 2
    }
}