package com.example.auth.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.core.ui.DefaultTextColor
import com.core.ui.Mat3Primary
import com.core.ui.composable.ButtonG
import com.example.auth.ui.states.SignUpEvent
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhoneView(onEvent: (SignUpEvent) -> Unit) {
    var phoneNoText by remember {
        mutableStateOf("")
    }
    Box {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        Column {
            Row(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp)
            ) {
                OutlinedTextField(
                    value = phoneNoText,
                    onValueChange = {
                        if (it.length <= 10) {
                            if (it.length == 10) keyboardController?.hide()
                            phoneNoText = it
                        }
                    },
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(color = DefaultTextColor),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .testTag(Tags.PHONE_ENTER),
                    label = {
                        Text(text = "Phone No")
                    },
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Phone
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = DefaultTextColor,
                        focusedTextColor = DefaultTextColor,
                        focusedContainerColor = Mat3Primary,
                        focusedLabelColor = Mat3Primary,
                        unfocusedLabelColor = DefaultTextColor,
                        cursorColor = Mat3Primary
                    )
                )
            }
            ButtonG(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 12.dp)
                    .fillMaxWidth(),
                text = "Next"
            ) {
                onEvent(SignUpEvent.SendOtp(phoneNoText))
            }
        }
    }
}
