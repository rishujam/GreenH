package com.ev.greenh.grow.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.Mat3Bg
import com.core.ui.Mat3OnBg
import com.core.ui.Mat3OnSurfaceVariant
import com.core.ui.NunitoFontFamily
import com.core.ui.composable.ButtonG
import com.core.ui.composable.Toolbar
import com.core.ui.findActivity
import com.ev.greenh.R
import com.ev.greenh.grow.ui.composable.components.GroupBadgeIcon
import com.ev.greenh.grow.ui.model.GrowDetailData
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.ui.plants.PlantFragment
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

@Composable
fun GrowDetailScreen(data: GrowDetailData?) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Mat3Bg)
    ) {
        val content = LocalContext.current
        Column(modifier = Modifier.fillMaxSize()) {
            data?.let {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Toolbar(title = "", icon = R.drawable.back_btn)
                    Image(
                        painter = painterResource(
                            id = R.drawable.img
                        ),
                        contentDescription = Tags.GROW_DETAIL_TOP_IMAGE,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Box(modifier = Modifier.padding(top = 16.dp, start = 16.dp)) {
                    Text(
                        text = data.plantName,
                        fontSize = 20.sp,
                        fontFamily = NunitoFontFamily,
                        color = Mat3OnBg,
                        fontWeight = FontWeight.Bold
                    )
                }
                GroupBadgeIcon(
                    Modifier,
                    data = data.requirements
                )
                Text(
                    text = "Steps to grow",
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = NunitoFontFamily,
                    fontSize = 18.sp,
                    color = Mat3OnSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    data.steps.forEachIndexed { index, step ->
                        item {
                            Text(
                                text = "${index + 1}. $step",
                                fontFamily = NunitoFontFamily,
                                fontSize = 16.sp,
                                color = Mat3OnBg,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    ButtonG(modifier = Modifier, text = "Buy Plant") {
                        val activity = content.findActivity()
                        val fragment = PlantFragment()
                        (activity as? MainActivity)?.setCurrentFragmentBack(fragment)
                    }
                }

            } ?: run {
                //TODO Show Error Message UI
            }
        }

    }
}