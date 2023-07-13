package com.ev.greenh.auth.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ev.greenh.auth.ui.SignUpViewModel
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
        Row {
            Text(
                text = viewModel.state.value.phoneNo,
                color = Color.Black,
                modifier = Modifier,
                fontSize = 16.sp
            )
            Text(
                color = DefaultTextColor,
                modifier = Modifier
                    .padding(start = 8.dp),
                text = "Wrong Number?",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
        OtpTextField(
            otpText = otpValue,
            onOtpTextChange = { value, otpInputField ->
                otpValue = value
            }
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Button(
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
        Row {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Didn't Received OTP?",
                fontSize = 16.sp,
                color = DefaultTextColor
            )
            Text(
                modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                text = "Resend in 45 sec",
                color = DefaultTextColor,
                fontSize = 16.sp
            )
        }

    }
}

@Composable
fun OtpEditText() {
    var otp1 by remember {
        mutableStateOf("")
    }
    var otp2 by remember {
        mutableStateOf("")
    }
    var otp3 by remember {
        mutableStateOf("")
    }
    var otp4 by remember {
        mutableStateOf("")
    }
    var otp5 by remember {
        mutableStateOf("")
    }
    var otp6 by remember {
        mutableStateOf("")
    }
    Row(modifier = Modifier.padding(top = 16.dp)) {
        Box {
            BasicTextField(
                value = otp1,
                onValueChange = {},
                modifier = Modifier
                    .width(44.dp)
                    .aspectRatio(1f)
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp2,
                onValueChange = {},
                modifier = Modifier
                    .width(44.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black,
                    cursorColor = MediumGreen,
                    textColor = DefaultTextColor
                )
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp3,
                onValueChange = {},
                modifier = Modifier
                    .width(44.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black,
                    cursorColor = MediumGreen,
                    textColor = DefaultTextColor
                )
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp4,
                onValueChange = {},
                modifier = Modifier
                    .width(44.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black,
                    cursorColor = MediumGreen,
                    textColor = DefaultTextColor
                )
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp5,
                onValueChange = {},
                modifier = Modifier
                    .width(44.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black,
                    cursorColor = MediumGreen,
                    textColor = DefaultTextColor
                )
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp6,
                onValueChange = {},
                modifier = Modifier
                    .width(44.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black,
                    cursorColor = MediumGreen,
                    textColor = DefaultTextColor
                )
            )
        }
    }
}