package com.sailinghawklabs.photonotes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sailinghawklabs.photonotes.R

private val OpenSans = FontFamily(
    Font(R.font.opensans_regular)
)

private val OpenSansCondensed = FontFamily(
    Font(R.font.opensans_condensed_medium),
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge =  TextStyle(
        fontFamily = OpenSansCondensed,
        fontWeight = FontWeight.W600,
        fontSize = 57.sp,
    ),

    displayMedium =  TextStyle(
        fontFamily = OpenSansCondensed,
        fontWeight = FontWeight.W600,
        fontSize = 48.sp,
    ),

    displaySmall = TextStyle(
        fontFamily = OpenSansCondensed,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
    ),

    headlineLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W600,
        fontSize = 32.sp,
    ),

    headlineSmall = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),

    titleLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),

    titleMedium = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    ),

    titleSmall = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),

    bodyMedium = TextStyle(
        fontFamily = OpenSans,
        fontSize = 14.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),

    labelMedium = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    labelSmall = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.W500,
        fontSize = 10.sp

    )
)