package com.ev.greenh.auth.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.ev.greenh.R
import com.ev.greenh.auth.ui.SignUpViewModel
import com.ev.greenh.commonui.DarkGreen
import com.ev.greenh.commonui.DefaultTextColor
import com.ev.greenh.commonui.LightBgGreen
import com.ev.greenh.commonui.MediumGreen

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */


@Composable
fun VerifyPhoneView(viewModel: SignUpViewModel) {
    var otpValue by remember {
        mutableStateOf("")
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
        ConstraintLayout(constraints, modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.layoutId("title")) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    colorFilter = ColorFilter.tint(MediumGreen),
                    contentDescription = "back_button",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Verification ${viewModel.state.value.phoneNo}",
                    color = MediumGreen,
                    fontSize = 16.sp
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
                    onClick = { /*TODO*/ },
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
            Text(
                modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                text = "Resend in 45 sec",
                color = DefaultTextColor,
                fontSize = 14.sp
            )
        }

    }
}