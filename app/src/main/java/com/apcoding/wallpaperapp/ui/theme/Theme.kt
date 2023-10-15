package com.apcoding.wallpaperapp.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(
    primary = Purple200,
    onPrimary = Color.White,
    secondary = Color.LightGray,
    background = Color(0xFF1B2936),
    surface = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    onPrimary = Color.White,
    secondary = Color(0xFFFA5A4B),
    background = Color.White,
    surface = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun WallPaperAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}