package com.rewtio.tugasku.preferences

sealed class AppSettingsKey(val key: String) {
    object KEY_THEME_MODE : AppSettingsKey("themeMode")
    object KEY_LANGUAGE : AppSettingsKey("language")
}