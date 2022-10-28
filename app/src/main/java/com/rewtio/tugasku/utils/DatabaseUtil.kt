package com.rewtio.tugasku.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.rewtio.tugasku.BuildConfig

object DatabaseUtil {
    val Context.dataStore by preferencesDataStore(name = "${BuildConfig.APPLICATION_ID}_preferences")
}