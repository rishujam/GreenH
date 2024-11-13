package com.example.auth.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.DefaultTextColor
import com.core.ui.Mat3Primary
import com.core.ui.composable.ButtonG
import com.core.ui.composable.TextG
import com.example.auth.ui.events.SignUpEvents
import com.example.auth.ui.states.SignUpState
import kotlinx.coroutines.delay

/*
 * Created by Sudhanshu Kumar on 12/11/24.
 */
 
@Composable
fun VerifyPhone(
    onEvent: (SignUpEvents) -> Unit,
    state: SignUpState,
    buildVersion: Int?
) {
    var otpValue by remember {
        mutableStateOf("")
    }
    var timeLeft by remember {
        mutableIntStateOf(45)
    }
    LaunchedEffect(state.isTimerRunning) {
        if(state.isTimerRunning) {
            timeLeft = 45
            while(timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
            onEvent(SignUpEvents.ShowResendButton)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp)
    ) {
        TextG(
            text = state.phoneNo,
            textColor = Mat3Primary,
            textSize = 16.sp,
            icon = Icons.Outlined.KeyboardArrowLeft,
            iconSize = 24.dp,
            iconTint = Mat3Primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            onEvent(SignUpEvents.WrongNo)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("otp_box"),
            horizontalArrangement = Arrangement.Center
        ) {
            OtpTextField(
                otpText = otpValue,
                onOtpTextChange = { value, otpInputField ->
                    otpValue = value
                }
            )
        }
        ButtonG(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            text = "Verify"
        ) {
            onEvent(SignUpEvents.VerifyClick(otpValue, buildVersion!!))
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextG(
                text = "Didn't Received OTP?",
                textSize = 14.sp,
                textColor = DefaultTextColor,
                modifier = Modifier
            )
            if(state.isTimerRunning) {
                TextG(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Resend in $timeLeft sec",
                    textColor = DefaultTextColor,
                    textSize = 14.sp,
                )
            }
            if (!state.isTimerRunning) {
                TextG(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Resend OTP",
                    textSize = 14.sp, textColor = Mat3Primary,
                    fontWeight = FontWeight.Bold
                ) {
                    timeLeft = 45
                    onEvent(SignUpEvents.ResendOtp)
                }
            }
        }
    }
}