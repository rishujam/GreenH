package com.ev.greenh.profile.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.Mat3OnBg
import com.core.ui.Mat3OnPrimary
import com.core.ui.Mat3OnSurfaceVariant
import com.core.ui.Mat3Primary
import com.core.ui.Mat3SurfaceVariant
import com.core.ui.NunitoFontFamily
import com.core.ui.composable.AlertPrompt
import com.core.ui.composable.ButtonG
import com.core.ui.composable.TextG
import com.core.ui.model.ButtonType
import com.core.ui.model.AlertModel
import com.core.ui.model.AlertType
import com.ev.greenh.profile.ProfileEvents
import com.ev.greenh.profile.ProfileState

/*
 * Created by Sudhanshu Kumar on 05/11/24.
 */

@Composable
fun LoggedInProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvents) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        state.alert?.let {
            AlertPrompt(
                model = state.alert,
                onCancel = { onEvent(ProfileEvents.AlertCancel) }
            ) {
                when(state.alert.type) {
                    is AlertType.LogoutConfirmation -> {
                        onEvent(ProfileEvents.LogoutConfirm)
                    }

                    is AlertType.DeleteConfirmation -> {
                        onEvent(ProfileEvents.DeleteAccountConfirm)
                    }

                    else -> {}
                }
            }
        }
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
            TextG(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                text = state.profile?.phone.orEmpty(),
                textSize = 14.sp,
                textColor = Mat3OnPrimary,
                fontFamily = NunitoFontFamily,
                iconTint = Mat3OnPrimary,
                icon = Icons.Filled.Phone,
                iconSize = 20.dp
            )
            TextG(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                text = state.profile?.emailId.orEmpty(),
                textSize = 14.sp,
                textColor = Mat3OnPrimary,
                fontFamily = NunitoFontFamily,
                iconTint = Mat3OnPrimary,
                icon = Icons.Filled.Email,
                iconSize = 20.dp
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.weight(1f))
                ButtonG(
                    text = if(state.profile?.profileComplete == true) {
                        "Edit"
                    } else {
                        "Complete profile"
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
                    buttonType = ButtonType.SecondaryEnabled,
                    buttonPadding = 8.dp
                ) {
                    onEvent(ProfileEvents.Edit)
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .background(color = Mat3SurfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onEvent(ProfileEvents.Contact)
                    }
            ) {
                Row {
                    Text(
                        text = "Contact",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Mat3OnSurfaceVariant,
                            fontFamily = NunitoFontFamily,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = if(state.contactExpanded) {
                            Icons.Outlined.KeyboardArrowUp
                        } else {
                            Icons.Outlined.KeyboardArrowDown
                        },
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Mat3OnSurfaceVariant
                    )
                }
                AnimatedVisibility(visible = state.contactExpanded) {
                    Text(
                        text = "rishuparashar7@gmail.com",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Mat3OnSurfaceVariant,
                            fontFamily = NunitoFontFamily
                        )
                    )
                }

            }
            Divider(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                color = Mat3OnSurfaceVariant,
                thickness = 0.5.dp
            )
            TextG(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                text = "Delete account",
                textSize = 16.sp,
                textColor = Mat3OnSurfaceVariant,
                fontFamily = NunitoFontFamily
            ) {
                onEvent(ProfileEvents.DeleteAccount)
            }
            Divider(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                color = Mat3OnSurfaceVariant,
                thickness = 0.5.dp
            )
            TextG(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 16.dp),
                text = "Logout",
                textSize = 16.sp,
                textColor = Mat3OnSurfaceVariant,
                fontFamily = NunitoFontFamily
            ) {
                onEvent(ProfileEvents.Logout)
            }
        }
    }
}