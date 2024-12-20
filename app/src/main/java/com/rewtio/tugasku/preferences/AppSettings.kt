package com.rewtio.tugasku.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rewtio.tugasku.ui.theme.ThemeMode
import com.rewtio.tugasku.utils.DatabaseUtil.dataStore
import com.rewtio.tugasku.utils.LocaleUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

object AppSettings {
    /*Some settings are changed and need to be saved to the database*/
    private var isChanged = MutableStateFlow(false)
    val isChangedFlow: StateFlow<Boolean> = isChanged.asStateFlow()

    private val themeMode = MutableStateFlow(ThemeMode.AUTO)
    val themeModeFlow: StateFlow<ThemeMode>
        get() = themeMode.asStateFlow()

    fun setThemeMode(themeMode: ThemeMode) {
        this.themeMode.value = themeMode
        isChanged.value = true
    }

    private val locale = MutableStateFlow<LocaleUtils.Language>(LocaleUtils.Language.Default)
    val localeState: StateFlow<LocaleUtils.Language>
        get() = locale.asStateFlow()

    fun setLocale(locale: LocaleUtils.Language) {
        this.locale.value = locale
        isChanged.value = true
    }

    suspend fun fetchSettings(context: Context) {
        context.dataStore.data.first().let { pref ->
            themeMode.value =
                pref[stringPreferencesKey(AppSettingsKey.KEY_THEME_MODE.key)]?.let {
                    ThemeMode.valueOf(it)
                } ?: ThemeMode.AUTO

            locale.value = LocaleUtils.Language.getLang(
                pref[stringPreferencesKey(AppSettingsKey.KEY_LANGUAGE.key)] ?: "auto"
            )
        }
    }

    suspend fun saveSettings(context: Context) {
        if (isChangedFlow.value) {
            context.dataStore.edit {
                it[stringPreferencesKey(AppSettingsKey.KEY_THEME_MODE.key)] = themeMode.value.toString()
                it[stringPreferencesKey(AppSettingsKey.KEY_LANGUAGE.key)] = locale.value.toString()
            }
            isChanged.emit(false)
        }
    }
}