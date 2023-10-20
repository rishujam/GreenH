package com.ev.greenh.commonui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ev.greenh.R
import com.ev.greenh.commonui.DarkGreen
import com.ev.greenh.commonui.Mat3OnBg
import com.ev.greenh.commonui.Mat3OnSurfaceVariant
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 16/10/23.
 */

@Composable
fun Toolbar(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp)
    ) {
        Row {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_back_arrow
                ),
                contentDescription = Tags.TOOLBAR_BACK_BTN,
                colorFilter = ColorFilter.tint(Mat3OnBg)
            )
            Text(
                text = title,
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Mat3OnBg
                )
            )
        }
    }
}