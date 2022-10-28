package com.rewtio.tugasku

import android.app.Application
import android.content.Context
import com.rewtio.tugasku.database.TugasDatabase
import com.rewtio.tugasku.preferences.AppSettings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TugasKuApplication : Application() {
    val database: TugasDatabase by lazy { TugasDatabase.getInstance(this) }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        GlobalScope.launch {
            AppSettings.instance.fetchSettings(this@TugasKuApplication)
        }
    }
}