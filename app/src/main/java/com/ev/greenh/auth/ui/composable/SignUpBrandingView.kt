package com.ev.greenh.auth.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ev.greenh.R
import com.ev.greenh.commonui.DefaultTextColor
import com.ev.greenh.commonui.MediumGreen

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
                        fontFamily = FontFamily(Font(R.font.carmen_bold)),
                        fontSize = 24.sp
                    )
                }
            }
            Row(modifier = Modifier.align(Alignment.End)) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = "Sustainability starts at home",
                    fontFamily = FontFamily(Font(R.font.carmen_regular)),
                    fontSize = 18.sp,
                    color = DefaultTextColor
                )
            }
        }
    }
}