package com.ev.greenh.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.Mat3OnBg
import com.core.ui.Mat3OnPrimary
import com.core.ui.Mat3Primary
import com.core.ui.NunitoFontFamily
import com.core.ui.composable.GButton
import com.core.ui.composable.LoadingAnimation
import com.core.ui.composable.TextIcon
import com.core.ui.model.ButtonType

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
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = "Profile",
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Mat3OnBg,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily
                    )
                )
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .background(color = Mat3Primary)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        text = "Hi ${state.profile?.name.orEmpty()}!",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Mat3OnPrimary,
                            fontFamily = NunitoFontFamily,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    TextIcon(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                        text = state.profile?.phone.orEmpty(),
                        textSize = 14.sp,
                        textColor = Mat3OnPrimary,
                        fontWeight = FontWeight.Normal,
                        fontFamily = NunitoFontFamily,
                        iconTint = Mat3OnPrimary,
                        icon = Icons.Filled.Phone,
                        iconSize = 20.dp
                    )
                    TextIcon(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                        text = state.profile?.emailId.orEmpty(),
                        textSize = 14.sp,
                        textColor = Mat3OnPrimary,
                        fontWeight = FontWeight.Normal,
                        fontFamily = NunitoFontFamily,
                        iconTint = Mat3OnPrimary,
                        icon = Icons.Filled.Email,
                        iconSize = 20.dp
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(Modifier.weight(1f))
                        GButton(
                            text = "Edit",
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
                            buttonType = ButtonType.SecondaryEnabled,
                            buttonPadding = 8.dp
                        ) {
                            onEvent(ProfileEvents.EditClick)
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = state.isLoggedIn == false,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GButton(modifier = Modifier, text = "Create or Login") {
                    onEvent(ProfileEvents.AuthClick)
                }
            }
        }
        if(state.isLoading == true) {
            LoadingAnimation()
        }
    }
}