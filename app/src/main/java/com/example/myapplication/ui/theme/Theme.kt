package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.White,
    secondary = OrangeLight,
    onSecondary = TextDark,
    secondaryContainer = OrangeLight,
    onSecondaryContainer = TextDark,
    background = Cream,
    onBackground = TextDark,
    surface = Cream,
    onSurface = TextDark,
    surfaceVariant = Color.White,
    onSurfaceVariant = GrayText,
    outline = TextDark,
    outlineVariant = Color(0xFFD0D0D0),
    error = RedAccent,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.White,
    secondary = OrangeLight,
    onSecondary = TextDark,
    secondaryContainer = DarkOrange,
    onSecondaryContainer = DarkText,
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkBackground,
    onSurface = DarkText,
    surfaceVariant = DarkCard,
    onSurfaceVariant = DarkGrayText,
    outline = Color(0xFF9E9289),
    outlineVariant = Color(0xFF4A413B),
    error = Color(0xFFEF5350),
    onError = Color.White
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(0.dp),
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp),
    extraLarge = RoundedCornerShape(0.dp)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
