package com.example.ui.listing

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

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
        detachAndScrapAttachedViews(recycler)
        for (position in 0 until itemCount) {
            val view = recycler.getViewForPosition(position)
            addView(view)

//            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
//            val density = context.resources.displayMetrics.density
//            val margin = (16 * density).toInt()
//            val totalHorizontalMargin = (32 * density).toInt()
//            layoutParams.width = if(isViewCompletelyVisible(view, height)) {
//                width - totalHorizontalMargin
//            } else {
//                width - totalHorizontalMargin * 2
//            }
//            layoutParams.height = (width.toDouble() * 1.4).toInt()
//            view.layoutParams = layoutParams
//
//            val left = if(isViewCompletelyVisible(view, height)) {
//                margin
//            } else {
//                totalHorizontalMargin
//            }
//            val right = if(isViewCompletelyVisible(view, height)) {
//                width - margin
//            } else {
//                width - totalHorizontalMargin
//            }
//            val top = position * layoutParams.height - offset
//            val bottom = top + layoutParams.height - if(isViewCompletelyVisible(view, height)) {
//                totalHorizontalMargin
//            } else {
//                totalHorizontalMargin * 2
//            }

            val layoutParams = view.layoutParams as RecyclerView.LayoutParams
            val density = context.resources.displayMetrics.density
            val margin = (16 * density).toInt()
            layoutParams.width = width
            layoutParams.height = (layoutParams.width.toDouble() * 1.4).toInt()
            measureChild(view, width, height)

            val left = 0
            val right = width
            val top = position * layoutParams.height - offset
            val bottom = top + layoutParams.height
            layoutDecorated(view, left, top, right, bottom)
        }
    }

    private fun isViewCompletelyVisible(view: View?, recyclerViewHeight: Int): Boolean {
        if (view == null) return false
        val viewTop = getDecoratedTop(view)
        val viewBottom = getDecoratedBottom(view)
        return viewTop >= 0 && viewBottom <= recyclerViewHeight
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
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
}