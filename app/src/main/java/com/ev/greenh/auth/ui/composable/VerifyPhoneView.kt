package com.ev.greenh.auth.ui.composable

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.ev.greenh.R
import com.ev.greenh.auth.ui.SignUpViewModel
import com.ev.greenh.auth.ui.events.SignUpEvents
import com.ev.greenh.commonui.DarkGreen
import com.ev.greenh.commonui.DefaultTextColor
import com.ev.greenh.commonui.LightBgGreen
import com.ev.greenh.commonui.MediumGreen
import com.ev.greenh.util.findActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */


@Composable
fun VerifyPhoneView(viewModel: SignUpViewModel) {
    var otpValue by remember {
        mutableStateOf("")
    }
    var timer by remember {
        mutableStateOf(45)
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
    val context = LocalContext.current
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
        ConstraintLayout(constraints, modifier = Modifier.fillMaxWidth()) {
            val interactionSource = remember { MutableInteractionSource() }
            Row(
                modifier = Modifier
                    .layoutId("title")
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        viewModel.onEvent(SignUpEvents.WrongNo)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    colorFilter = ColorFilter.tint(MediumGreen),
                    contentDescription = "back_button",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = AnnotatedString(viewModel.state.value.phoneNo),
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
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.onEvent(
                            SignUpEvents.VerifyClick(
                                otpValue,
                                context.getString(R.string.user_ref),
                                context.getString(R.string.token)
                            )
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
                .fillMaxWidth(),
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
                        val options = buildResendOtpOptions(viewModel, context)
                        viewModel.onEvent(SignUpEvents.ResendOtp(options))
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

private fun buildResendOtpOptions(
    viewModel: SignUpViewModel,
    context: Context
): PhoneAuthOptions? {
    val resendToken = viewModel.resendToken
    val phone = viewModel.state.value.phoneNo
    if (phone.length == 10 && resendToken != null) {
        return PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$phone")
            .setTimeout(15L, TimeUnit.SECONDS)
            .setActivity(context.findActivity())
            .setCallbacks(viewModel.callbacks)
            .setForceResendingToken(resendToken)
            .build()
    }
    return null
}