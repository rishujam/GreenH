package com.ev.greenh.grow.ui.composable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ev.greenh.commonui.Mat3OnBg
import com.ev.greenh.commonui.Mat3Surface
import com.ev.greenh.commonui.Mat3Tertiary
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

@Composable
fun BadgeIcon(res: Int, des: String) {
    Column {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Mat3Surface),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = res),
                contentDescription = Tags.GROW_DETAIL_REQ_BADGE_IMG,
                modifier = Modifier.size(42.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier.width(54.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = des,
                color = Mat3OnBg
            )
        }
    }
}