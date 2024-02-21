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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.core.ui.DefaultTextColor
import com.core.ui.LightBgGreen
import com.core.ui.MediumGreen
import com.example.auth.ui.SignUpViewModel
import com.example.auth.ui.events.SignUpEvents
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 06/07/23.
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhoneView(viewModel: SignUpViewModel) {
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
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = DefaultTextColor,
                        textColor = DefaultTextColor,
                        focusedBorderColor = MediumGreen,
                        focusedLabelColor = MediumGreen,
                        unfocusedLabelColor = DefaultTextColor,
                        cursorColor = MediumGreen
                    )
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 12.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(Tags.PHONE_VIEW_NEXT_BTN),
                    onClick = {
                        viewModel.onEvent(SignUpEvents.SendOtp(phoneNoText))
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LightBgGreen,
                        contentColor = MediumGreen
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 6.dp, bottom = 6.dp)
                            .semantics {
                                contentDescription = "NextBtn SignUp"
                            }
                            .testTag("NextBtn SignUp"),
                        text = "Next",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
