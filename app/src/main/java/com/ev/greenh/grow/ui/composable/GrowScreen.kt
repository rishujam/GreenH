package com.ev.greenh.grow.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ev.greenh.R
import com.ev.greenh.common.commonui.composable.SearchBar
import com.ev.greenh.common.commonui.composable.Toolbar
import com.ev.greenh.grow.ui.composable.components.GrowScreenMainRvItem
import com.ev.greenh.grow.ui.model.GrowListingItem

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

@Composable
fun GrowScreen(
    data: List<GrowListingItem>,
    itemClickBlock: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Toolbar(title = "How To Grow", icon = R.drawable.back_btn)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            SearchBar(hint = "Search Plant")
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(data) {
                    GrowScreenMainRvItem(it, itemClickBlock)
                }
            }
        }
    }
}