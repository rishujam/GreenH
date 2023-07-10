package com.ev.greenh.auth.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ev.greenh.R
import com.ev.greenh.auth.SignUpViewModel
import com.ev.greenh.commonui.DefaultTextColor
import com.ev.greenh.commonui.MediumGreen
import com.ev.greenh.commonui.TextWhite

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */


@Composable
fun SignUpBrandingView() {
    Box {
        Column {
            Row {
                Image(
                    painter = painterResource(
                        id = R.drawable.logo_gh
                    ),
                    contentDescription = "brand_logo",
                    modifier = Modifier
                        .size(92.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier.align(Alignment.Bottom)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "GardnersHub",
                        color = MediumGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }
            }
            Row(modifier = Modifier.align(Alignment.End)) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = "Sustainability starts at home",
                    fontSize = 18.sp,
                    color = DefaultTextColor
                )
            }
        }
    }
}