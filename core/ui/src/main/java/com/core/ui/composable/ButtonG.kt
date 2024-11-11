package com.core.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.Mat3OnPrimary
import com.core.ui.Mat3OnSurfaceVariant
import com.core.ui.Mat3Outline
import com.core.ui.Mat3Primary
import com.core.ui.Mat3Surface
import com.core.ui.Mat3SurfaceVariant
import com.core.ui.NunitoFontFamily
import com.core.ui.model.ButtonType

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun ButtonG(
    modifier: Modifier,
    text: String,
    buttonType: ButtonType = ButtonType.PrimaryEnabled,
    buttonPadding: Dp = 12.dp,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                when (buttonType) {
                    is ButtonType.PrimaryEnabled -> Mat3Primary
                    is ButtonType.PrimaryDisabled -> Mat3Surface
                    is ButtonType.SecondaryEnabled -> Mat3SurfaceVariant
                }
            )
            .padding(buttonPadding)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                if (buttonType == ButtonType.PrimaryEnabled || buttonType == ButtonType.SecondaryEnabled) {
                    onClick()
                }
            },
        text = text,
        color = when(buttonType) {
            is ButtonType.PrimaryEnabled -> Mat3OnPrimary
            is ButtonType.PrimaryDisabled -> Mat3Outline
            is ButtonType.SecondaryEnabled -> Mat3OnSurfaceVariant
        },
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Bold
    )
}