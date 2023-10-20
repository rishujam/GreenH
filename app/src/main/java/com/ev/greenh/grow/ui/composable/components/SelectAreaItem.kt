package com.ev.greenh.grow.ui.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ev.greenh.commonui.Mat3OnSecondary
import com.ev.greenh.commonui.Mat3OnSurfaceVariant
import com.ev.greenh.commonui.Mat3Secondary
import com.ev.greenh.commonui.Mat3SurfaceVariant

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun SelectAreaItem(
    area: String,
    position: Int,
    currentSelected: Int,
    onClick: () -> Unit
) {
    Text(
        text = area,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if(position == currentSelected) {
                    Mat3Secondary
                } else {
                    Mat3SurfaceVariant
                }
            )
            .padding(vertical = 12.dp)
            .clickable {
                onClick()
            },
        fontSize = 16.sp,
        color = if(position == currentSelected) {
            Mat3OnSecondary
        } else {
            Mat3OnSurfaceVariant
        }
    )
}