package com.core.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.core.ui.Mat3Outline
import com.core.ui.NunitoFontFamily
import com.core.ui.model.DialogModel

/*
 * Created by Sudhanshu Kumar on 14/11/23.
 */

@Composable
fun AlertPrompt(
    model: DialogModel,
    cancelText: String? = null,
    confirmText: String? = null,
    onCancel: (() -> Unit)? = null,
    onConfirm: (() -> Unit)? = null
) {
    Dialog(
        onDismissRequest = {
            onCancel?.let { onCancel() }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .border(1.dp, color = Mat3Outline, shape = RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = model.message,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    cancelText?.let {
                        GButton(
                            modifier = Modifier,
                            text = cancelText
                        ) {
                            onCancel?.let { onCancel() }
                        }
                    }
                    confirmText?.let {
                        GButton(
                            modifier = Modifier,
                            text = confirmText
                        ) {
                            onConfirm?.let { onConfirm() }
                        }
                    }
                }
            }
        }
    }
}