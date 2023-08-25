package com.ev.greenh.auth.ui.composable

import android.app.Activity
import android.content.Context
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
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.ev.greenh.auth.ui.SignUpViewModel
import com.ev.greenh.auth.ui.events.SignUpEvents
import com.ev.greenh.commonui.DefaultTextColor
import com.ev.greenh.commonui.LightBgGreen
import com.ev.greenh.commonui.MediumGreen
import com.ev.greenh.util.findActivity
import com.example.testing.Tags
import com.example.testing.TestConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import java.util.concurrent.TimeUnit

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
                val context = LocalContext.current
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(Tags.PHONE_VIEW_NEXT_BTN),
                    onClick = {
                        viewModel.onEvent(SignUpEvents.NextClick(phoneNoText))
                        val options =
                            buildOptionsForPhoneVerification(viewModel, phoneNoText, context)
                        if (options != null) {
                            viewModel.onEvent(SignUpEvents.OtpOption(options, null, phoneNoText))
                        } else {
                            viewModel.onEvent(SignUpEvents.OtpOption(null, "Invalid number", null))
                        }
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

private fun buildOptionsForPhoneVerification(
    viewModel: SignUpViewModel,
    phone: String,
    context: Context
): PhoneAuthOptions? {
    if (phone.length == 10 && phone != TestConstants.UI_TEST_NUMBER) {
        return PhoneAuthOptions.newBuilder(viewModel.getAuth())
            .setPhoneNumber("+91$phone")
            .setTimeout(0L, TimeUnit.SECONDS)
            .setActivity(context.findActivity())
            .setCallbacks(viewModel.callbacks)
            .build()
    }
    return null
}
