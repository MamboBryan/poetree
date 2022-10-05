package com.mambo.poetree.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    error = ErrorDark,
    onError = OnErrorDark,
)

private val LightColorPalette = lightColors(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    surface = Surface,
    onSurface = OnSurface,
    background = Background,
    onBackground = OnBackground,
    error = Error,
    onError = OnError,
)

@Composable
fun PoetreeTheme(
//    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    SideEffect {
        when (isDarkTheme) {
            true -> {
//                systemUiController.setStatusBarColor(color = DarkSurface)
            }
            false -> {
//                systemUiController.setStatusBarColor(color = Surface)
            }
        }
    }

    val colors = when (isDarkTheme) {
        true -> DarkColorPalette
        false -> LightColorPalette
    }

    MaterialTheme(
        colors = colors, typography = Typography, shapes = Shapes, content = content
    )

}