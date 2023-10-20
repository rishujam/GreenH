package com.ev.greenh.grow.ui.composable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ev.greenh.R
import com.ev.greenh.grow.ui.model.LocalPlantListItem
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun LocalPlantItem(item: LocalPlantListItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.logo_gh
            ),
            contentDescription = Tags.LOCAL_PLANT_LIST_ITEM_IMAGE,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}