package com.rewtio.tugasku.ui.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rewtio.tugasku.preferences.ThemeMode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    val KEY_THEME = stringPreferencesKey("theme")

    val themeMode = dataStore.data.map {
        ThemeMode.valueOf(it[KEY_THEME] ?: "AUTO")
    }

    fun changeTheme(theme: ThemeMode) {
        viewModelScope.launch {
            dataStore.edit {
                it[KEY_THEME] = theme.name
            }
        }
    }
}