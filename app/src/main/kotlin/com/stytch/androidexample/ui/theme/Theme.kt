package com.stytch.androidexample.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val StytchColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color.White,
    secondary = Color.White,
    background = OffWhite,
)

@Composable
fun StytchAndroidExampleTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = StytchColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
