package com.nvshink.effectivetestcase.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Green,
    onPrimary = White,
    secondary = Glass,
    surface = Dark,
    surfaceContainer = DarkGray,
    surfaceBright = LightGray,
    outline = Stroke,
    onSurface = Caption
)

private val LightColorScheme = lightColorScheme(
    primary = Green,
    onPrimary = Dark,
    secondary = Glass,
    surface = White,
    surfaceContainer = LightGray,
    surfaceBright = LightGray,
    outline = Stroke,
    onSurface = Caption
)

//    Dynamic theme is disabled to comply with the design of the test case
@Composable
fun EffectiveTestCaseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }

    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}