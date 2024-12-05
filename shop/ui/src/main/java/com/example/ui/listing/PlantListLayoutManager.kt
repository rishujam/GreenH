package com.example.ui.listing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

/*
 * Created by Sudhanshu Kumar on 03/12/24.
 */

class PlantListLayoutManager(
    private val context: Context
) : RecyclerView.LayoutManager() {

    private var offset = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        fill(recycler)
    }

    private fun fill(recycler: RecyclerView.Recycler?) {
        if (itemCount == 0 || recycler == null) return
        val density = context.resources.displayMetrics.density
        val margin = (32 * density).toInt()
        detachAndScrapAttachedViews(recycler)
        var accumulatedTop = -offset
        for (position in 0 until itemCount) {
            val view = recycler.getViewForPosition(position)
            addView(view)

            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
            layoutParams.width = if(isViewCompletelyVisible(view, height)) {
                width
            } else {
                width - margin
            }
            layoutParams.height = (layoutParams.width.toDouble() * 1.6).toInt()
            view.layoutParams = layoutParams
            measureChild(view, width, height)

            if (position > 0) {
                accumulatedTop += margin
            }
            val left = if(isViewCompletelyVisible(view, height)) {
                0
            } else {
                margin / 2
            }
            val right = if(isViewCompletelyVisible(view, height)) {
                width
            } else {
                width - (margin / 2)
            }
            val top = accumulatedTop
            val bottom = accumulatedTop + layoutParams.height
            layoutDecorated(view, left, top, right, bottom)
            accumulatedTop += layoutParams.height
            updateViewVisibilityAndAnimations(position)
        }
    }

    private fun isViewCompletelyVisible(view: View?, recyclerViewHeight: Int): Boolean {
        if (view == null) return false
        val viewTop = getDecoratedTop(view)
        val viewBottom = getDecoratedBottom(view)
        val viewHeight = getDecoratedMeasuredHeight(view)
        val visibleTop = viewTop.coerceAtLeast(0)
        val visibleBottom = viewBottom.coerceAtMost(recyclerViewHeight)
        val visibleHeight = visibleBottom - visibleTop

        return visibleHeight >= 0.6 * viewHeight
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun isSmoothScrolling(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        if (recycler == null || itemCount == 0) return 0
        val totalScrollableHeight = itemCount * height
        val recyclerViewHeight = height
        val newOffset = offset + dy
        val clampedOffset = newOffset.coerceIn(0, totalScrollableHeight - recyclerViewHeight)
        val delta = clampedOffset - offset
        offsetChildrenVertical(-delta)
        offset = clampedOffset
        fill(recycler)
        return delta
    }

    private fun updateViewVisibilityAndAnimations(i: Int) {
        val view = getChildAt(i)
        val is70PercentVisible = isViewCompletelyVisible(view, height)
        if (is70PercentVisible) {
            view?.animate()
                ?.alpha(1f)
                ?.setDuration(300)
                ?.start()
        } else {
            view?.animate()
                ?.alpha(0.5f)
                ?.setDuration(300)
                ?.start()
        }
    }
}