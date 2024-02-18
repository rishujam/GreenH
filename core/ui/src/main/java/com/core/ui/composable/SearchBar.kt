package com.core.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.ui.DefaultTextColor
import com.core.ui.MediumGreen

/*
 * Created by Sudhanshu Kumar on 16/10/23.
 */

@Composable
fun SearchBar(hint: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp)
    ) {
        var textValue by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = textValue,
            onValueChange = { textValue = it },
            label = { Text(text = hint) },
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = DefaultTextColor,
                textColor = DefaultTextColor,
                focusedBorderColor = MediumGreen,
                focusedLabelColor = MediumGreen,
                unfocusedLabelColor = DefaultTextColor,
                cursorColor = MediumGreen
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}