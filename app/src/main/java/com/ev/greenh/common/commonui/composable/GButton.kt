package com.ev.greenh.common.commonui.composable

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
import com.ev.greenh.common.commonui.Mat3OnPrimary
import com.ev.greenh.common.commonui.Mat3OnSecondary
import com.ev.greenh.common.commonui.Mat3OnSurfaceVariant
import com.ev.greenh.common.commonui.Mat3Primary
import com.ev.greenh.common.commonui.Mat3Secondary
import com.ev.greenh.common.commonui.Mat3SurfaceVariant
import com.ev.greenh.common.commonui.NunitoFontFamily

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun GButton(
    modifier: Modifier,
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isEnabled) {
                    Mat3Primary
                } else {
                    Mat3SurfaceVariant
                }
            )
            .padding(vertical = 12.dp)
            .clickable {
                if(isEnabled) {
                    onClick()
                }
            },
        text = text,
        color = if (isEnabled) {
            Mat3OnPrimary
        } else {
            Mat3OnSurfaceVariant
        },
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Bold
    )
}