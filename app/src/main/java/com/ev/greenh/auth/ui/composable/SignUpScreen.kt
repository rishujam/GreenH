package com.ev.greenh.auth.ui.composable

import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ev.greenh.auth.ui.SignUpViewModel
import com.ev.greenh.auth.ui.events.SignUpUiEvents
import com.ev.greenh.auth.ui.states.SignUpProgress
import com.ev.greenh.commonui.MediumGreen
import com.ev.greenh.ui.MainActivity
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
    var isVisiblePhoneView by remember {
        mutableStateOf(false)
    }
    var isVisibleVerifyView by remember {
        mutableStateOf(false)
    }
    var isVisibleProgress by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SignUpUiEvents.ShowToast -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is SignUpUiEvents.ScreenChanged -> {
                    when (event.screen) {
                        is SignUpProgress.VerifyPhoneStage -> {
                            isVisiblePhoneView = false
                            isVisibleVerifyView = true
                        }

                        is SignUpProgress.EnterPhoneStage -> {
                            isVisibleVerifyView = false
                            isVisiblePhoneView = true
                        }

                        is SignUpProgress.VerifiedPhoneStage -> {
                            val activity = context.findActivity()
                            activity.startActivity(Intent(context, MainActivity::class.java))
                            activity.finish()
                        }
                    }
                }

                is SignUpUiEvents.Loading -> {
                    isVisibleProgress = event.isLoading
                }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = isVisibleProgress,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(bottom = 44.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = MediumGreen)
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
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
            AnimatedVisibility(
                visible = isVisibleVerifyView,
                enter = slideInVertically(
                    initialOffsetY = {
                        it / 2
                    }
                ) + fadeIn(),
                exit = fadeOut()
            ) {
                VerifyPhoneView(viewModel)
            }
        }
    }
}