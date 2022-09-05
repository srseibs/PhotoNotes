package com.sailinghawklabs.photonotes.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)



sealed class ThemeColors(
    val card: Color,
    val cardAlternate: Color,
    val background: Color,
    val primary: Color,
    val text: Color
) {
    object Day : ThemeColors(
        card = Color(0xFFBBDEFB),
        cardAlternate = Color(0xFF8ED4A0),
        background = Color(0xFFF3E5F5),
        primary = Color(0xFF006AF6),
        text = Color(0xFF000000)
    )

    object Night : ThemeColors(
        card = Color(0xFF020D23),
        cardAlternate = Color(0xFF133A41),
        background = Color(0xFF0C3C98),
        primary = Color(0xFF172840),
        text = Color(0xffffffff)
    )
}