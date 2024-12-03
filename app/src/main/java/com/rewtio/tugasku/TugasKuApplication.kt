package com.rewtio.tugasku

import android.app.Application
import android.content.Context
import com.rewtio.tugasku.database.TugasDatabase
import com.rewtio.tugasku.preferences.AppSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TugasKuApplication : Application() {
    val database: TugasDatabase by lazy { TugasDatabase.getInstance(this) }
    private val ioScope = CoroutineScope(Job() + Dispatchers.IO)

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ioScope.launch {
            AppSettings.fetchSettings(this@TugasKuApplication)
        }
    }
}