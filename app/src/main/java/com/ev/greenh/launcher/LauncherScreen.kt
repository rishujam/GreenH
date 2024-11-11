package com.ev.greenh.launcher

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.Mat3OnSurfaceVariant
import com.core.ui.MediumGreen
import com.core.ui.composable.ButtonG
import com.core.ui.composable.TextG
import com.core.ui.model.ButtonType
import com.ev.greenh.R
import com.example.testing.Tags

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
            Image(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(id = R.drawable.launcher_screen_img2),
                contentDescription = "Launcher Image",
                contentScale = ContentScale.Crop
            )
            TextG(
                modifier = Modifier.padding(top = 16.dp),
                text = "Welcome to Gardeners Hub",
                textSize = 20.sp,
                textColor = Mat3OnSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
            TextG(
                modifier = Modifier.padding(top = 8.dp),
                text = "Discover world of plants with our comprehensive ",
                textSize = 14.sp,
                textColor = Mat3OnSurfaceVariant
            )
            ButtonG(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .fillMaxWidth(),
                text = "Sign Up"
            ) {
                onEvent(LauncherEvent.SignIn)
            }
            ButtonG(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .fillMaxWidth(),
                text = "Skip Sign Up",
                buttonType = ButtonType.SecondaryEnabled
            ) {
                onEvent(LauncherEvent.Skip)
            }
        }
    }
}