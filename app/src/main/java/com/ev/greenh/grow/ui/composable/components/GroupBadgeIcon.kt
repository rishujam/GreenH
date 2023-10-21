package com.ev.greenh.grow.ui.composable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ev.greenh.grow.ui.values.Orientation
import com.ev.greenh.grow.ui.model.BadgeIconData

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

@Composable
fun GroupBadgeIcon(
    modifier: Modifier,
    orientation: Orientation,
    data: List<BadgeIconData>
) {
    when(orientation) {
        is Orientation.Horizontal -> {
            Row(modifier = modifier.fillMaxWidth()) {
                for(i in data) {
                    BadgeIcon(res = i.imgRes, des = i.des, title = i.title)
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
        is Orientation.Vertical -> {
            Column(modifier = modifier) {
                for(i in data) {
                    BadgeIcon(res = i.imgRes, des = i.des, title = i.title)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}