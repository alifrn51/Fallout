package com.fall.fallout.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Yellow700,
    secondary = Black,
    // Other default colors to override
    background = Black,
    onBackground = White,
    surface = Gray500,
    onSurface = White,
    onPrimary = White,
    onSecondary = White,
    error = Red500,
    onError = White
)


@Composable
fun FalloutTheme(content: @Composable () -> Unit) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}