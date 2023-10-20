package com.ev.greenh.grow.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ev.greenh.commonui.Mat3Bg
import com.ev.greenh.commonui.Mat3OnBg
import com.ev.greenh.commonui.Mat3OnSurfaceVariant
import com.ev.greenh.commonui.Mat3Surface
import com.ev.greenh.commonui.Mat3SurfaceVariant
import com.ev.greenh.commonui.composable.GButton
import com.ev.greenh.commonui.composable.Toolbar
import com.ev.greenh.grow.ui.LocalPlantListFragment
import com.ev.greenh.grow.ui.composable.components.SelectAreaItem
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.DummyData
import com.ev.greenh.util.findActivity

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun LocalPlantStep1Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Mat3Bg)
    ) {
        val context = LocalContext.current
        Toolbar(title = "Local Plants")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
        ) {
            Text(
                text = "Select Your Area",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Mat3SurfaceVariant)
                    .padding(vertical = 12.dp),
                fontSize = 16.sp,
                color = Mat3OnSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
        }
        var selectedIndex by remember{ mutableIntStateOf(-1) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
        ) {
            val areaList = DummyData.getListOfState()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                areaList.forEachIndexed { index, item ->
                    item {
                        SelectAreaItem(item, index, selectedIndex) {
                            selectedIndex = index
                        }
                    }
                }
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            text = "This will help us find plants suitable for your environment.",
            color = Mat3OnBg
        )
        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)) {
            GButton(
                Modifier, text = "Next", selectedIndex != -1
            ) {
                val activity = context.findActivity()
                val fragment = LocalPlantListFragment()
                (activity as? MainActivity)?.setCurrentFragmentBack(fragment)
            }
        }

    }
}

