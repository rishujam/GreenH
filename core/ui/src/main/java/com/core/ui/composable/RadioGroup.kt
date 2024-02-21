package com.core.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.core.ui.Mat3OnSecondary
import com.core.ui.model.RadioBtnOption

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

@Composable
fun RadioGroup(
    list: List<RadioBtnOption>,
    textColor: Color
) {
    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            list.forEachIndexed { index, s ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Mat3OnSecondary,
                            unselectedColor = Mat3OnSecondary
                        )
                    )
                    Text(
                        text = s.title,
                        color = textColor
                    )
                }
            }
        }
    }
}