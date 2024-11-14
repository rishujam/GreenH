package com.ev.greenh.launcher

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.Mat3OnSurfaceVariant
import com.core.ui.composable.ButtonG
import com.core.ui.composable.TextG
import com.core.ui.model.ButtonType
import com.ev.greenh.R
import com.example.auth.ui.composable.SignUpBrandingView

/*
 * Created by Sudhanshu Kumar on 07/11/24.
 */
 
@Composable
fun LauncherScreen(
    state: LauncherState,
    onEvent: (LauncherEvent) -> Unit
) {
    if(state.configLoaded) {
        onEvent(LauncherEvent.ConfigLoaded)
    }
    if(state.isLoggedIn == true || state.isLoginSkippedEarlier == true) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.app_icon1
                ),
                contentDescription = "Launcher Icon",
                modifier = Modifier
                    .size(64.dp)
            )
        }
    } else if(state.isLoggedIn == false && state.isLoginSkippedEarlier == false) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SignUpBrandingView()
            }
            ButtonG(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 12.dp)
                    .fillMaxWidth(),
                text = "Sign Up"
            ) {
                onEvent(LauncherEvent.SignIn)
            }
            ButtonG(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 12.dp)
                    .fillMaxWidth(),
                text = "Skip Sign Up",
                buttonType = ButtonType.SecondaryEnabled
            ) {
                onEvent(LauncherEvent.Skip)
            }
        }
    }
}