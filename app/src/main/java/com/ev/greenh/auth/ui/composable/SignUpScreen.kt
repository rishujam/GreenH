package com.ev.greenh.auth.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.ev.greenh.auth.SignUpViewModel
import kotlinx.coroutines.delay

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

@Composable
fun SignUpScreen(viewModel: SignUpViewModel) {
    var isVisibleBranding by remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(
        visible = isVisibleBranding,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SignUpBrandingView()
            }
        }
    }
    LaunchedEffect(Unit) {
        delay(300)
        isVisibleBranding = true
    }
}