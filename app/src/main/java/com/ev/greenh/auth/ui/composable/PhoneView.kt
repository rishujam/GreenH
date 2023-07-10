package com.ev.greenh.auth.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ev.greenh.commonui.DefaultTextColor
import com.ev.greenh.commonui.LightBgGreen
import com.ev.greenh.commonui.MediumGreen

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */

@Preview
@Composable
fun PhoneView() {
    Box {
        Column {
            Row(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp)
            ) {
                TextFieldPhone()
            }
            Row(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LightBgGreen,
                        contentColor = MediumGreen
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
                        text = "Next",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun TextFieldPhone(
    modifier: Modifier = Modifier
) {
    var text by remember {
        mutableStateOf("")
    }
    Box(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                if (it.length <= 10) text = it
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = DefaultTextColor),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            label = {
                Text(text = "Phone No")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = DefaultTextColor,
                textColor = DefaultTextColor,
                focusedBorderColor = MediumGreen,
                focusedLabelColor = MediumGreen,
                unfocusedLabelColor = DefaultTextColor,
                cursorColor = MediumGreen
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}
