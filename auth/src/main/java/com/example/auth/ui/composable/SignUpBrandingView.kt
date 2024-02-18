package com.example.auth.ui.composable

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.core.ui.CarmenFontFamily
import com.core.ui.DefaultTextColor
import com.core.ui.MediumGreen
import com.example.auth.R
import com.example.testing.Tags

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
                        id = R.drawable.logo_ghh
                    ),
                    contentDescription = Tags.BRAND_LOGO,
                    modifier = Modifier
                        .size(92.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier.align(Alignment.Bottom)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .testTag(Tags.BRAND_NAME),
                        text = "GardnersHub",
                        color = MediumGreen,
                        fontFamily = CarmenFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                }
            }
            Row(modifier = Modifier.align(Alignment.End)) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .testTag(Tags.BRAND_DESCRIPTION),
                    text = "Sustainability starts at home",
                    fontFamily = CarmenFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = DefaultTextColor
                )
            }
        }
    }
}