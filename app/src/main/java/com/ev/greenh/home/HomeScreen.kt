package com.ev.greenh.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ev.greenh.R
import com.ev.greenh.commonui.Mat3OnSurfaceVariant
import com.ev.greenh.commonui.Mat3SurfaceVariant
import com.ev.greenh.commonui.composable.Toolbar
import com.ev.greenh.grow.ui.LocalPlantStep1Fragment
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.findActivity

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Toolbar(title = "GardnersHub", R.drawable.logo_gh)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Mat3SurfaceVariant)
                    .clickable {
                        val activity = context.findActivity()
                        val fragment = LocalPlantStep1Fragment()
                        (activity as? MainActivity)?.setCurrentFragmentBack(fragment)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Explore what to grow",
                    color = Mat3OnSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Mat3SurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "How to grow",
                    color = Mat3OnSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Mat3SurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Buy Plants",
                    color = Mat3OnSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

    }

}