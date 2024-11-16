package com.lalit.clean.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    primaryContainer = Red,
    background = Color.Black,
    surface = Color.Black,
    onSurface = Color.White,
    surfaceVariant = Color.Black,
    onSurfaceVariant = Color.White,
    secondaryContainer = Red,
    onSecondaryContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    primaryContainer = DarkGray,
    secondaryContainer = LightGray,
    background = Color.White,
    surfaceVariant = Color.White,
    surface = Color.White
)

/**
 * A Composable function that provides theming for the app, supporting both light and dark modes
 * as well as dynamic color on Android 12+.
 *
 * @param darkTheme Boolean to specify whether the dark theme should be applied.
 *                  Default is `isSystemInDarkTheme()` which follows the system's current theme.
 * @param dynamicColor Boolean to enable dynamic color support (Android 12+).
 *                    Default is `true` to enable dynamic color when possible.
 * @param content The content composable that will be styled with the defined theme.
 *
 * This function automatically adjusts the theme based on the system's settings or
 * user-defined preferences and applies dynamic color when supported.
 */
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

/**
 * Contains functions to access the current theme values provided at the call site's position in
 * the hierarchy.
 */
object AppTheme {
    /**
     * Retrieves the current [] at the call site's position in the hierarchy.
     */
    val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme

    /**
     * Retrieves the current [MaterialTheme.typography] at the call site's position in the hierarchy.
     */
    val typography: androidx.compose.material3.Typography
        @Composable @ReadOnlyComposable get() = MaterialTheme.typography
}