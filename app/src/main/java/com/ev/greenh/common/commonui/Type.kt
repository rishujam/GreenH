package com.ev.greenh.common.commonui

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ev.greenh.R

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */
 
val CarmenFontFamily = FontFamily(
    Font(R.font.carmen_heavy, FontWeight.Bold),
    Font(R.font.carmen_regular, FontWeight.Normal)
)

val NunitoFontFamily = FontFamily(
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_semi_bold, FontWeight.SemiBold),
    Font(R.font.nunito_regular, FontWeight.Normal)
)

val LogoFontFamily = FontFamily(
    Font(R.font.logo_pacifico_font, FontWeight.Normal)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = CarmenFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    h1 = TextStyle(
        fontFamily = CarmenFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
)