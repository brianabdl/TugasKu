package com.rewtio.tugasku.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.*


object LocaleUtils {
    sealed class Language(val code: String) {
        object Default : Language("auto")
        object Indonesian : Language("id")
        object English : Language("en")

        companion object {
            fun getLang(pos: Int): Language {
                return when (pos) {
                    1 -> Indonesian
                    2 -> English
                    else -> Default
                }
            }

            fun getLang(code: String) : Language {
                return when (code) {
                    "id" -> Indonesian
                    "en" -> English
                    else -> Default
                }
            }
        }

        override fun toString(): String {
            return code
        }
    }

    @Composable
    fun SetLanguage(lang: Language) {
        if (lang != Language.Default) {
            val locale = Locale(lang.code)
            val configuration = LocalConfiguration.current
            configuration.setLocale(locale)
            val resources = LocalContext.current.resources
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }
}