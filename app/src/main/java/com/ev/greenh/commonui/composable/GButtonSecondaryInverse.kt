package com.ev.greenh.commonui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ev.greenh.commonui.Mat3OnSecondary
import com.ev.greenh.commonui.Mat3Secondary

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

@Composable
fun GButtonSecondaryInverse(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Mat3OnSecondary)
            .padding(vertical = 6.dp, horizontal = 16.dp)
            .clickable {
                onClick()
            },
        text = text,
        color = Mat3Secondary,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )
}