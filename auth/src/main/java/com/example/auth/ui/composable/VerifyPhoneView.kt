package com.example.auth.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.core.ui.DefaultTextColor
import com.core.ui.LightBgGreen
import com.core.ui.MediumGreen
import com.example.auth.ui.SignUpViewModel
import com.example.auth.ui.events.SignUpEvents
import com.example.auth.ui.states.SignUpState
import com.example.testing.Tags
import kotlinx.coroutines.delay

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */


@Composable
fun VerifyPhoneView(
    onEvent: (SignUpEvents) -> Unit,
    state: SignUpState,
    buildVersion: Int?
) {

    var otpValue by remember {
        mutableStateOf("")
    }
    var timer by remember {
        mutableIntStateOf(45)
    }
    var isResendBtnVisible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = false) {
        while (true) {
            if (timer != 0) {
                delay(1000L)
                timer--
            } else {
                isResendBtnVisible = true
                break
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp)
    ) {
        val constraints = ConstraintSet {
            val verificationTitle = createRefFor("title")
            val otpBox = createRefFor("otp_box")
            val verifyButton = createRefFor("btn_verify")
            constrain(verificationTitle) {
                start.linkTo(otpBox.start)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            }
            constrain(otpBox) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(verificationTitle.bottom)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            }
            constrain(verifyButton) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(otpBox.start)
                end.linkTo(otpBox.end)
                top.linkTo(otpBox.bottom)
            }
        }
        ConstraintLayout(
            constraints,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(Tags.VERIFY_ENTER_OTP)
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            Row(
                modifier = Modifier
                    .layoutId("title")
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onEvent(SignUpEvents.WrongNo)
                    }
            ) {
                Image(
                    painter = painterResource(id = com.core.ui.R.drawable.ic_back_arrow),
                    colorFilter = ColorFilter.tint(MediumGreen),
                    contentDescription = "back_button",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = AnnotatedString(state.phoneNo),
                    style = TextStyle(
                        color = MediumGreen,
                        fontSize = 16.sp
                    )
                )
            }
            Row(
                modifier = Modifier
                    .layoutId("otp_box")
                    .padding(top = 8.dp)
            ) {
                OtpTextField(
                    otpText = otpValue,
                    onOtpTextChange = { value, otpInputField ->
                        otpValue = value
                    }
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .layoutId("btn_verify")
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(Tags.VERIFY_BTN),
                    onClick = {
                        onEvent(
                            SignUpEvents.VerifyClick(otpValue, buildVersion!!)
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LightBgGreen,
                        contentColor = MediumGreen
                    )
                ) {
                    Text(
                        text = "Verify",
                        modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(Tags.RESEND_OTP),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Didn't Received OTP?",
                fontSize = 14.sp,
                color = DefaultTextColor
            )
            if (!isResendBtnVisible) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                    text = "Resend in $timer sec",
                    color = DefaultTextColor,
                    fontSize = 14.sp,
                )
            }
            if (isResendBtnVisible) {
                ClickableText(
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                    onClick = {
                        onEvent(SignUpEvents.ResendOtp)
                    },
                    text = AnnotatedString("Resend OTP"),
                    style = TextStyle(
                        color = MediumGreen,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

    }
}