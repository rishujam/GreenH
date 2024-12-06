package com.example.ui.listing

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView


/*
 * Created by Sudhanshu Kumar on 03/12/24.
 */

class PlantListLayoutManager(
    private val context: Context?
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
        val density = context?.resources?.displayMetrics?.density ?: 1f
        val marginItem = (32 * density).toInt()
        val marginLeft = (16 * density).toInt()
        detachAndScrapAttachedViews(recycler)
        var accumulatedTop = -offset
        for (position in 0 until itemCount) {
            val view = recycler.getViewForPosition(position)
            addView(view)
            val isVisible = isViewMostlyVisible(view, height)
            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
            layoutParams.width = width - marginItem
            layoutParams.height = (layoutParams.width * 1.6).toInt()
            view.layoutParams = layoutParams
            measureChild(view, layoutParams.width, layoutParams.height)
            if(isVisible) {
                scaleView(
                    view,
                    1.1f,
                    1.1f
                )
            } else {
                scaleView(
                    view,
                    1f,
                    1f
                )
            }
            accumulatedTop += marginItem

            val right = width - marginLeft
            val top = accumulatedTop
            val bottom = accumulatedTop + layoutParams.height
            layoutDecorated(view, marginLeft, top, right, bottom)
            accumulatedTop += layoutParams.height
            updateViewVisibilityAndAnimations(position, isVisible)
        }
    }

    private fun isViewMostlyVisible(view: View?, recyclerViewHeight: Int): Boolean {
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

    private fun updateViewVisibilityAndAnimations(i: Int, isVisible: Boolean) {
        val view = getChildAt(i)
        if (isVisible) {
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

    fun scaleView(
        v: View,
        endX: Float,
        endScale: Float,
    ) {
        val anim: Animation = ScaleAnimation(
            1f,
            endX,
            1f,
            endScale,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.fillAfter = true
        anim.duration = 200L
        v.startAnimation(anim)
    }

}