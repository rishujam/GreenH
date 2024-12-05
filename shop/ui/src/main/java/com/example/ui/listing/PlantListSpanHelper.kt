package com.example.ui.listing

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

/*
 * Created by Sudhanshu Kumar on 05/12/24.
 */

class PlantListSpanHelper(
    private val context: Context?
) : LinearSnapHelper() {

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val itemCount = layoutManager.itemCount
        if (itemCount == 0) return RecyclerView.NO_POSITION

        val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        var closestChild: View? = null
        var closestDistance = Int.MAX_VALUE

        val recyclerViewCenterY = layoutManager.height / 2

        for (i in 0 until layoutManager.childCount) {
            val child = layoutManager.getChildAt(i) ?: continue
            val childCenterY =
                layoutManager.getDecoratedTop(child) + (layoutManager.getDecoratedMeasuredHeight(child) / 2)
            val distance = kotlin.math.abs(childCenterY - recyclerViewCenterY)
            if (distance < closestDistance) {
                closestDistance = distance
                closestChild = child
            }
        }

        return closestChild
    }

    override fun createScroller(layoutManager: RecyclerView.LayoutManager?): RecyclerView.SmoothScroller? {
        return layoutManager?.let {
            FastSmoothScroller(context)
        }
    }
}