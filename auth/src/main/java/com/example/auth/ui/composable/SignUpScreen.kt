package com.example.auth.ui.composable

import android.content.Intent
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.core.ui.LightBgGreen
import com.core.ui.MediumGreen
import com.core.ui.findActivity
import com.example.auth.ui.SignUpViewModel
import com.example.auth.ui.events.SignUpEvents
import com.example.auth.ui.states.SignUpProgress
import com.example.auth.ui.states.SignUpState
import com.example.testing.Tags
import kotlinx.coroutines.delay

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

@Composable
fun SignUpScreen(
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit,
    buildVersion: Int?,
    onSignUpSuccess: (() -> Unit)
) {
    val scaffoldState = rememberScaffoldState()
    var isVisibleBranding by remember {
        mutableStateOf(false)
    }
    var isVisibleProgress by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(state) {
        isVisibleProgress = state.loading
        state.error?.let {
            scaffoldState.snackbarHostState.showSnackbar(
                message = it
            )
            state.error = null
        }
        if(state.screen is SignUpProgress.VerifiedPhoneStage) {
            onSignUpSuccess()
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
                CircularProgressIndicator(
                    modifier = Modifier.testTag(Tags.SIGNUP_SCREEN_PROGRESS),
                    color = MediumGreen
                )
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
            }
            LaunchedEffect(Unit) {
                delay(300)
                isVisibleBranding = true
            }
            AnimatedVisibility(
                visible = state.screen is SignUpProgress.EnterPhoneStage,
                enter = slideInVertically(
                    initialOffsetY = {
                        it / 2
                    }
                ) + fadeIn(),
                exit = fadeOut()
            ) {
                PhoneView(onEvent)
            }
            if(state.screen is SignUpProgress.VerifyPhoneStage) {
                VerifyPhoneView(onEvent, state, buildVersion)
            }
        }
    }
}