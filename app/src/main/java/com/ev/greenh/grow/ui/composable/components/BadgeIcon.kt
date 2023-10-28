package com.ev.greenh.grow.ui.composable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ev.greenh.common.commonui.Mat3OnSurfaceVariant
import com.ev.greenh.common.commonui.Mat3SurfaceVariant
import com.ev.greenh.common.commonui.NunitoFontFamily
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

@Composable
fun BadgeIcon(res: Int, title: String, des: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Mat3SurfaceVariant)
    ) {
        Image(
            painter = painterResource(id = res),
            contentDescription = Tags.GROW_DETAIL_REQ_BADGE_IMG,
            modifier = Modifier
                .size(42.dp)
                .padding(start = 12.dp, top = 12.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = title,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 6.dp),
            fontSize = 14.sp,
            fontFamily = NunitoFontFamily,
            color = Mat3OnSurfaceVariant
        )
        Text(
            text = des,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 0.dp),
            fontSize = 14.sp,
            fontFamily = NunitoFontFamily,
            color = Mat3OnSurfaceVariant,
            fontWeight = FontWeight.SemiBold
        )
    }
}