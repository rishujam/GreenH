package com.ev.greenh.grow.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ev.greenh.commonui.Mat3Bg
import com.ev.greenh.commonui.Mat3Primary
import com.ev.greenh.commonui.composable.Toolbar

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun LocalPlantListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Mat3Bg)
    ) {
        Toolbar(title = "Local Plants")
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Mat3Primary)
        ) {

        }
    }
}
