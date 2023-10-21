package com.ev.greenh.grow.ui.composable.components

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ev.greenh.grow.ui.GrowConstants
import com.ev.greenh.grow.ui.GrowDetailFragment
import com.ev.greenh.grow.ui.values.Orientation
import com.ev.greenh.grow.ui.model.BadgeIconData
import com.ev.greenh.grow.ui.model.LocalPlantListItem
import com.ev.greenh.grow.ui.model.LocalPlantListQuestionItem
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Constants
import com.ev.greenh.util.findActivity

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

@Composable
fun GroupBadgeIcon(
    modifier: Modifier,
    data: List<BadgeIconData>
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        data.forEachIndexed { index, item ->
            item {
                BadgeIcon(res = item.imgRes, des = item.des, title = item.title)
            }
        }
    }
}