package org.tamersarioglu.easywidgets.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006A60),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF74F8E5),
    onPrimaryContainer = Color(0xFF00201C),
    secondary = Color(0xFF4A635F),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCCE8E2),
    onSecondaryContainer = Color(0xFF05201C),
    tertiary = Color(0xFF456179),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFCBE6FF),
    onTertiaryContainer = Color(0xFF001E30),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color.White,
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFAFDFB),
    onBackground = Color(0xFF191C1B),
    surface = Color(0xFFFAFDFB),
    onSurface = Color(0xFF191C1B),
    surfaceVariant = Color(0xFFDAE5E1),
    onSurfaceVariant = Color(0xFF3F4946),
    outline = Color(0xFF6F7976)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF53DBD0),
    onPrimary = Color(0xFF003732),
    primaryContainer = Color(0xFF005048),
    onPrimaryContainer = Color(0xFF74F8E5),
    secondary = Color(0xFFB1CCC6),
    onSecondary = Color(0xFF1C3531),
    secondaryContainer = Color(0xFF334B47),
    onSecondaryContainer = Color(0xFFCCE8E2),
    tertiary = Color(0xFFAACCE6),
    onTertiary = Color(0xFF0F344C),
    tertiaryContainer = Color(0xFF2B4962),
    onTertiaryContainer = Color(0xFFCBE6FF),
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
    onError = Color(0xFF690005),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF191C1B),
    onBackground = Color(0xFFE0E3E1),
    surface = Color(0xFF191C1B),
    onSurface = Color(0xFFE0E3E1),
    surfaceVariant = Color(0xFF3F4946),
    onSurfaceVariant = Color(0xFFBEC9C5),
    outline = Color(0xFF899390)
)

@Composable
fun EasyWidgetsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
} 