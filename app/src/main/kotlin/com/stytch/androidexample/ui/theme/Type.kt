package com.stytch.androidexample.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.stytch.androidexample.R

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.ibm_plex_sans_regular)),
        fontWeight = FontWeight.W600,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        textAlign = TextAlign.Center,
        color = Charcoal,
    ),
    h2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.ibm_plex_mono_regular)),
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 30.sp,
        textAlign = TextAlign.Start,
        color = Charcoal,
    ),
    button = TextStyle(
        fontFamily = FontFamily(Font(R.font.ibm_plex_sans_regular)),
        fontWeight = FontWeight.W600,
        fontSize = 18.sp,
        lineHeight = 32.sp,
        textAlign = TextAlign.Center,
        color = Charcoal,
    ),
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.ibm_plex_sans_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 18.sp,
        lineHeight = 25.sp,
        textAlign = TextAlign.Center,
        color = Disabled,
    ),
    body2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.ibm_plex_sans_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        textAlign = TextAlign.Start,
        color = Danger,
    ),
    caption = TextStyle(
        fontFamily = FontFamily(Font(R.font.ibm_plex_mono_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 28.sp,
        textAlign = TextAlign.Start,
        color = Charcoal,
    ),
)
