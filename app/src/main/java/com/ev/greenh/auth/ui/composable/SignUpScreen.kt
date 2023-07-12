package com.ev.greenh.auth.ui.composable

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ev.greenh.auth.ui.SignUpViewModel
import com.ev.greenh.auth.ui.events.SignUpUiEvents
import com.ev.greenh.auth.ui.states.SignUpProgress
import com.ev.greenh.util.findActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.util.concurrent.TimeUnit
import javax.annotation.meta.When

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

@Composable
fun SignUpScreen(viewModel: SignUpViewModel) {
    val scaffoldState = rememberScaffoldState()
    var isVisibleBranding by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is SignUpUiEvents.ShowToast -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is SignUpUiEvents.ScreenChanged -> {
                    when(event.screen) {
                        is SignUpProgress.VerifyPhoneStage -> {

                        }
                        is SignUpProgress.EnterPhoneStage -> {

                        }
                        is SignUpProgress.VerifiedPhoneStage -> {

                        }
                    }
                }
            }
        }
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
            exit = fadeOut()
        ) {
            PhoneView(viewModel)
        }
        LaunchedEffect(Unit) {
            delay(300)
            isVisiblePhoneView = true
        }
    }
}