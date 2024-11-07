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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.core.ui.MediumGreen
import com.example.auth.ui.events.SignUpEvents
import com.example.auth.ui.states.SignUpProgress
import com.example.auth.ui.states.SignUpState
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

@Composable
fun SignUpScreen(
    state: SignUpState,
    buildVersion: Int?,
    onEvent: (SignUpEvents) -> Unit,
) {
    if(state.screen is SignUpProgress.VerifiedPhoneStage) {
        (state.screen as? SignUpProgress.VerifiedPhoneStage)?.profile?.let { profile ->
            onEvent(SignUpEvents.SignUpSuccess(profile))
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
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
                VerifyPhoneView(onEvent, state, buildVersion)
            }
        }
    }
}