package com.example.auth.ui.composable

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.core.ui.Mat3Primary
import com.example.auth.ui.states.SignUpEvent
import com.example.auth.ui.states.SignUpOneTimeEvent
import com.example.auth.ui.states.SignUpProgress
import com.example.auth.ui.states.SignUpState
import com.example.testing.Tags
import kotlinx.coroutines.flow.Flow

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

@Composable
fun SignUpScreen(
    state: SignUpState,
    buildVersion: Int?,
    oneTimeEventFlow: Flow<SignUpOneTimeEvent>,
    onEvent: (SignUpEvent) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold (
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            if(state.screen is SignUpProgress.VerifiedPhoneStage) {
                (state.screen as? SignUpProgress.VerifiedPhoneStage)?.profile?.let { profile ->
                    onEvent(SignUpEvent.SignUpSuccess(profile))
                }
            }
            val lifecycleOwner = LocalLifecycleOwner.current
            LaunchedEffect(oneTimeEventFlow, lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    oneTimeEventFlow.collect {
                        when(it) {
                            is SignUpOneTimeEvent.ShowToast -> {
                                snackBarHostState.showSnackbar(
                                    message = it.msg
                                )
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = state.loading,
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
                        color = Mat3Primary
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
                        visible = true,
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
                    VerifyPhone(onEvent, state, buildVersion)
                }
            }
        }
    }

}