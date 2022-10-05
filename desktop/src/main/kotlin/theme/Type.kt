package com.mambo.poetree.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

val fonts = FontFamily(
    Font(resource = "fonts/regular.ttf", weight = FontWeight.Normal),
    Font(resource = "fonts/medium.ttf", weight = FontWeight.Medium),
    Font(resource = "fonts/bold.ttf", weight = FontWeight.Bold),
)

val Typography = Typography(
    body1 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Normal),

    h4 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Medium),

    h5 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Bold),

    h6 = TextStyle(fontFamily = fonts, fontWeight = FontWeight.Black)
)