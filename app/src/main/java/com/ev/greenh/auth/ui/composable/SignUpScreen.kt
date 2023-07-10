package com.ev.greenh.auth.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = isVisibleBranding,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SignUpBrandingView()
                }
            }
            LaunchedEffect(Unit) {
                delay(300)
                isVisibleBranding = true
            }
        }
        var isVisiblePhoneView by remember {
            mutableStateOf(false)
        }
        AnimatedVisibility(
            visible = isVisiblePhoneView,
            enter = slideInVertically(
                initialOffsetY = {
                    it / 2
                }
            ) + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            PhoneView()
        }
        LaunchedEffect(Unit) {
            delay(300)
            isVisiblePhoneView = true
        }
    }
}