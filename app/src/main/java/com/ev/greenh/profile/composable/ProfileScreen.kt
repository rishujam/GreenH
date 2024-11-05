package com.ev.greenh.profile.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.core.ui.composable.LoadingAnimation
import com.ev.greenh.profile.ProfileEvents
import com.ev.greenh.profile.ProfileState

/*
 * Created by Sudhanshu Kumar on 27/10/24.
 */

@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvents) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = state.isLoggedIn == true,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LoggedInProfileScreen(state = state) {
                onEvent(it)
            }
        }
        AnimatedVisibility(
            visible = state.isLoggedIn == false,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LoggedOutProfileScreen {
                onEvent(it)
            }
        }
        if(state.isLoading == true) {
            LoadingAnimation()
        }
    }
}