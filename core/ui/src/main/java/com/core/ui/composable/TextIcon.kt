package com.core.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.Mat3OnPrimary
import com.core.ui.NunitoFontFamily

/*
 * Created by Sudhanshu Kumar on 27/10/24.
 */

@Composable
fun TextIcon(
    modifier: Modifier,
    icon: ImageVector,
    text: String,
    iconTint: Color,
    iconSize: Dp,
    textSize: TextUnit,
    textColor: Color,
    fontFamily: FontFamily,
    fontWeight: FontWeight
) {
    Row(modifier = modifier) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = iconTint
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = TextStyle(
                fontSize = textSize,
                color = textColor,
                fontFamily = fontFamily,
                fontWeight = fontWeight,
            )
        )
    }
}