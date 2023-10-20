package com.ev.greenh.commonui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ev.greenh.commonui.Mat3OnBg
import com.ev.greenh.commonui.Mat3OnSecondary
import com.ev.greenh.grow.ui.model.Option

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

@Composable
fun RadioGroup(
    list: List<Option>,
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