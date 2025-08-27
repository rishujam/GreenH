package com.core.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
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
import com.core.ui.model.AlertModel
import com.core.ui.model.ButtonType

/*
 * Created by Sudhanshu Kumar on 14/11/23.
 */

@Composable
fun AlertPrompt(
    model: AlertModel,
    onCancel: (() -> Unit)? = null,
    onConfirm: (() -> Unit)? = null
) {
    AnimatedVisibility(
        visible = model.isShowing
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
                elevation = CardDefaults.cardElevation(5.dp),
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        model.cancelText?.let {
                            ButtonG(
                                modifier = Modifier,
                                text = model.cancelText,
                                buttonType = ButtonType.SecondaryEnabled
                            ) {
                                onCancel?.let { onCancel() }
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        model.confirmText?.let {
                            ButtonG(
                                modifier = Modifier,
                                text = model.confirmText
                            ) {
                                onCancel?.let { onCancel() }
                                onConfirm?.let { onConfirm() }
                            }
                        }
                    }
                }
            }
        }
    }
}