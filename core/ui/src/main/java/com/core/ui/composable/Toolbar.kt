package com.core.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.Mat3OnBg
import com.core.ui.NunitoFontFamily
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 16/10/23.
 */

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String,
    iconEnd: Int? = null,
    iconEndClick: (() -> Unit)? = null,
    icon: Int? = null,
    iconClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp)
    ) {
        Row {
            icon?.let {
                Image(
                    painter = painterResource(
                        id = icon
                    ),
                    contentDescription = Tags.TOOLBAR_BACK_BTN,
                    colorFilter = ColorFilter.tint(Mat3OnBg),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            iconClick?.let { iconClick() }
                        },
                )
            }
            Text(
                text = title,
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Mat3OnBg,
                    fontFamily = NunitoFontFamily
                )
            )
            iconEnd?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            iconEndClick?.let { iconEndClick() }
                        },
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = Tags.TOOLBAR_END_BTN,
                        colorFilter = ColorFilter.tint(Mat3OnBg),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}