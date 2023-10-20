package com.ev.greenh.grow.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ev.greenh.R
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

@Composable
fun GrowDetailScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier)
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
        }

    }
}