package com.core.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.ui.DefaultTextColor
import com.core.ui.Mat3Primary

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
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = DefaultTextColor,
                focusedTextColor = DefaultTextColor,
                focusedContainerColor = Mat3Primary,
                focusedLabelColor = Mat3Primary,
                unfocusedLabelColor = DefaultTextColor,
                cursorColor = Mat3Primary
            ),
            shape = RoundedCornerShape(8.dp)
        )
    }
}