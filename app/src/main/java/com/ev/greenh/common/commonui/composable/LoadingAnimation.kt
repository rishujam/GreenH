package com.ev.greenh.common.commonui.composable

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.ev.greenh.common.commonui.Mat3Primary
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 22/10/23.
 */

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier
) {
    val animation = rememberInfiniteTransition(label = Tags.LOADING_ANIM)
    val progress by animation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart,
        ),
        label = Tags.LOADING_ANIM_PROGRESS
    )

    Box(
        modifier = modifier
            .size(60.dp)
            .scale(progress)
            .alpha(1f - progress)
            .border(
                5.dp,
                color = Mat3Primary,
                shape = CircleShape
            )
    )
}