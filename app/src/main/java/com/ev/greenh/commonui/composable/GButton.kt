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
import com.ev.greenh.commonui.Mat3OnPrimary
import com.ev.greenh.commonui.Mat3Primary

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun GButton(
    modifier: Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Mat3Primary)
            .padding(vertical = 12.dp)
            .clickable {},
        text = text,
        color = Mat3OnPrimary,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    )
}