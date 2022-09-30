package com.rewtio.tugasku.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import com.rewtio.tugasku.preferences.AppSettings

enum class ThemeMode {
    AUTO, LIGHT, DARK
}

@Composable
fun TugasKuTheme(content: @Composable () -> Unit) {

    val theme by AppSettings.instance.themeModeFlow.collectAsState()

    val value = when (theme) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        else -> isSystemInDarkTheme()
    }

    MaterialTheme(
        colorScheme = if (value) darkColorScheme() else lightColorScheme(),
        typography = Typography,
        content = content
    )
}