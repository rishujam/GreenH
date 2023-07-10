package com.ev.greenh.auth.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import com.ev.greenh.commonui.LightBgGreen
import com.ev.greenh.commonui.MediumGreen

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */

@Preview
@Composable
fun VerifyPhoneView() {
    Column {
        Row {
            Text(
                text = "9999999999",
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Text(
                color = MediumGreen,
                modifier = Modifier
                    .padding(start = 16.dp),
                text = "Wrong Number?",
                fontWeight = FontWeight.Bold
            )
        }
        OtpEditText()
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                border = BorderStroke(width = 1.dp, brush = SolidColor(Color.White)),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LightBgGreen,
                    contentColor = MediumGreen
                )
            ) {
                Text(text = "Verify")
            }
        }
        Row {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                text = "Didn't Received OTP?"
            )
            Text(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                text = "Resend in 45 sec",
                fontWeight = FontWeight.Bold,
                color = MediumGreen
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
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp1,
                onValueChange = {},
                modifier = Modifier
                    .width(36.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black)
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp2,
                onValueChange = {},
                modifier = Modifier
                    .width(36.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black)
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp3,
                onValueChange = {},
                modifier = Modifier
                    .width(36.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black)
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp4,
                onValueChange = {},
                modifier = Modifier
                    .width(36.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black)
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp5,
                onValueChange = {},
                modifier = Modifier
                    .width(36.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black)
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp)) {
            OutlinedTextField(
                value = otp6,
                onValueChange = {},
                modifier = Modifier
                    .width(36.dp)
                    .aspectRatio(1f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MediumGreen,
                    unfocusedBorderColor = Color.Black)
            )
        }
    }
}