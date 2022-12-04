package com.fall.fallout.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Black,
    secondary = Yellow700,
    // Other default colors to override
    background = Black,
    surface = Gray500,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White,
)


@Composable
fun FalloutTheme( content: @Composable () -> Unit) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}