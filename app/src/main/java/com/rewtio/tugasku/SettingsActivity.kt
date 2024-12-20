package com.rewtio.tugasku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rewtio.tugasku.preferences.AppSettings
import com.rewtio.tugasku.ui.SettingsItem
import com.rewtio.tugasku.ui.theme.ThemeMode
import com.rewtio.tugasku.ui.theme.TugasKuTheme
import com.rewtio.tugasku.utils.LocaleUtils

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TugasKuTheme {
                val context = LocalContext.current
                val isChanged by AppSettings.isChangedFlow.collectAsState()
                val theme by AppSettings.themeModeFlow.collectAsState()
                val language by AppSettings.localeState.collectAsState()

                LocaleUtils.SetLanguage(language)

                LaunchedEffect(isChanged) {
                    if (isChanged) {
                        AppSettings.saveSettings(context)
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(stringResource(R.string.settings))
                            },
                            navigationIcon = {
                                IconButton(onClick = { (context as ComponentActivity).finish() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                                }
                            }
                        )
                    }
                ) { pad ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pad)
                    ) {
                        Text(
                            text = stringResource(R.string.theme),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )

                        SettingsItem(
                            title = stringResource(R.string.theme_mode),
                            icon = when (theme) {
                                ThemeMode.AUTO -> {
                                    if (isSystemInDarkTheme()) {
                                        Icons.Outlined.DarkMode
                                    } else {
                                        Icons.Outlined.LightMode
                                    }
                                }
                                ThemeMode.DARK -> {
                                    Icons.Outlined.DarkMode
                                }
                                ThemeMode.LIGHT -> {
                                    Icons.Outlined.LightMode
                                }
                            },
                            description = "This Mode will be applied to all the app",
                            items = listOf("Follow System", "Light Mode", "Dark Mode"),
                            onItemClick = {
                                AppSettings.setThemeMode(ThemeMode.entries[it])
                            }
                        )

//            Text(
//                text = stringResource(R.string.language),
//                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
//            )
//
//            SettingsItem(
//                title = stringResource(R.string.language),
//                icon = Icons.Filled.Language,
//                description = "Pilih bahasa yang ingin digunakan",
//                items = listOf("Default", "Indonesia", "English"),
//                onItemClick = {
//                    val lang = LocaleUtils.Language.getLang(it)
//                    AppSettings.setLocale(lang)
//                }
//            )

//            SettingsItem(
//                title = stringResource(R.string.help_translate),
//                icon = Icons.Filled.Translate,
//                description = "Bantu kami untuk menerjemahkan aplikasi ini"
//            )
//
//            Text(
//                text = stringResource(R.string.update),
//                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
//            )
//            SettingsItem(
//                title = stringResource(R.string.option_update),
//                icon = Icons.Filled.Update,
//                description = "Pilih versi pembaruan terbaru pada aplikasi",
//                items = listOf("Stable", "Beta")
//            )
                    }
                }
            }
        }
    }
}