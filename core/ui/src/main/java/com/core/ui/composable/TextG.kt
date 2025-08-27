package com.core.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.core.ui.NunitoFontFamily

/*
 * Created by Sudhanshu Kumar on 27/10/24.
 */

@Composable
fun TextG(
    modifier: Modifier,
    icon: ImageVector? = null,
    text: String,
    iconTint: Color? = null,
    iconSize: Dp? = null,
    textSize: TextUnit,
    textColor: Color,
    fontFamily: FontFamily = NunitoFontFamily,
    fontWeight: FontWeight = FontWeight.Normal,
    onClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            onClick?.let { onClick() }
        }
    ) {
        icon?.let {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(iconSize ?: 16.dp),
                tint = iconTint ?: Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
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