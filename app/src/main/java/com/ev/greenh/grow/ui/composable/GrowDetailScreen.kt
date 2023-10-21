package com.ev.greenh.grow.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ev.greenh.R
import com.ev.greenh.commonui.Mat3OnBg
import com.ev.greenh.grow.ui.composable.components.GroupBadgeIcon
import com.ev.greenh.grow.ui.model.GrowDetailData
import com.ev.greenh.grow.ui.values.Orientation
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

@Composable
fun GrowDetailScreen(data: GrowDetailData?) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            data?.let {
                Image(
                    painter = painterResource(
                        id = R.drawable.img
                    ),
                    contentDescription = Tags.GROW_DETAIL_TOP_IMAGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)),
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier.padding(top = 16.dp, start = 16.dp)) {
                    Text(
                        text = data.plantName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Mat3OnBg
                    )
                }
                Text(
                    text = "Requirements",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Mat3OnBg,
                    modifier = Modifier.padding(top = 12.dp, start = 16.dp)
                )
                GroupBadgeIcon(
                    Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    orientation = Orientation.Horizontal,
                    data = data.requirements
                )
            } ?: run {
                //TODO Show Error Message UI
            }
        }

    }
}