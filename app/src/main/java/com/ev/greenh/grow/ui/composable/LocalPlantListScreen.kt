package com.ev.greenh.grow.ui.composable

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ev.greenh.R
import com.ev.greenh.commonui.Mat3Bg
import com.ev.greenh.commonui.composable.Toolbar
import com.ev.greenh.grow.ui.GrowConstants
import com.ev.greenh.grow.ui.GrowDetailFragment
import com.ev.greenh.grow.ui.LocalPlantListViewModel
import com.ev.greenh.grow.ui.composable.components.LocalPlantItem
import com.ev.greenh.grow.ui.composable.components.LocalPlantQuestionItem
import com.ev.greenh.grow.ui.model.LocalPlantListItem
import com.ev.greenh.grow.ui.model.LocalPlantListQuestionItem
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Constants
import com.ev.greenh.util.DummyData
import com.ev.greenh.util.findActivity

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun LocalPlantListScreen(
    viewModel: LocalPlantListViewModel
) {
    val list = DummyData.getLocalPlantList()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Mat3Bg)
    ) {
        Toolbar(title = "Local Plants", R.drawable.back_btn)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {
            list.forEachIndexed { index, item ->
                when(item.viewType) {
                    Constants.ViewType.LOCAL_PLANT_LIST_QUESTION -> {
                        item(
                            span = StaggeredGridItemSpan.FullLine
                        ) {
                            (item as? LocalPlantListQuestionItem)?.let {
                                LocalPlantQuestionItem(it, viewModel)
                            }
                        }
                    }
                    Constants.ViewType.LOCAL_PLANT_LIST -> {
                        item {
                            (item as? LocalPlantListItem)?.let {
                                LocalPlantItem(it) {
                                    val activity = context.findActivity()
                                    val bundle = Bundle()
                                    bundle.putString(GrowConstants.FragmentArgKeys.PLANT_ID, it.id)
                                    val fragment = GrowDetailFragment()
                                    fragment.arguments = bundle
                                    (activity as? MainActivity)?.setCurrentFragmentBack(fragment)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
