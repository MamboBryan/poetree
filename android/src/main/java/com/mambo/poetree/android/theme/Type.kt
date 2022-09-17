package com.mambo.poetree.android.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.mambo.poetree.android.R

val fonts = FontFamily(
    Font(resId = R.font.google_sans_regular, weight = FontWeight.Normal),
    Font(resId = R.font.google_sans_medium, weight = FontWeight.Medium),
    Font(resId = R.font.google_sans_bold, weight = FontWeight.Bold),
)

val Typography = Typography(
    body1 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Normal),

    h4 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Medium),

    h5 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Bold),

    h6 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Black)
)