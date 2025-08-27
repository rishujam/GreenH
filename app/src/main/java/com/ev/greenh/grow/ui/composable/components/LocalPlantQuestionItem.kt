package com.ev.greenh.grow.ui.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.Mat3OnSecondary
import com.core.ui.Mat3Secondary
import com.core.ui.composable.GButtonSecondaryInverse
import com.core.ui.composable.RadioGroup
import com.ev.greenh.grow.ui.LocalPlantListViewModel
import com.ev.greenh.grow.ui.events.LocalPlantListEvent
import com.ev.greenh.grow.ui.model.LocalPlantListQuestionItem

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

@Composable
fun LocalPlantQuestionItem(
    item: LocalPlantListQuestionItem,
    viewModel: LocalPlantListViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Mat3Secondary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = item.heading,
                color = Mat3OnSecondary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Text(
                text = item.question,
                color = Mat3OnSecondary
            )
            RadioGroup(item.radioBtnOptions, Mat3OnSecondary)
            item.radioBtnOptions.getOrNull(0)?.imageUrl?.let {
                Text(
                    text = "View Images",
                    color = Mat3OnSecondary
                )
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(8.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                GButtonSecondaryInverse(
                    modifier = Modifier,
                    text = "Submit"
                ) {
                   viewModel.onClick(LocalPlantListEvent.OnQuestionSubmitClick)
                }
            }

        }
    }
}