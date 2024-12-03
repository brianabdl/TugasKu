package com.rewtio.tugasku.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

object DatabaseUtil {
    val Context.dataStore by preferencesDataStore(name = "tugasku_preferences")
}