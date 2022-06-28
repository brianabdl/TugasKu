package com.rewtio.tugasku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rewtio.tugasku.preferences.ThemeMode
import com.rewtio.tugasku.ui.SettingsItem
import com.rewtio.tugasku.ui.theme.TugasKuTheme
import com.rewtio.tugasku.ui.theme.orInLightTheme
import com.rewtio.tugasku.ui.viewmodel.SettingsViewModel
import com.rewtio.tugasku.ui.viewmodel.ThemeViewModel
import kotlinx.coroutines.launch

class SettingsActivity : ComponentActivity() {

    private val vm: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val appSettings = remember {
                ThemeViewModel(context.dataStore)
            }
            val theme by appSettings.themeMode.collectAsState(initial = ThemeMode.AUTO)

            TugasKuTheme(
                darkTheme = when (theme) {
                    ThemeMode.DARK -> true
                    ThemeMode.LIGHT -> false
                    else -> isSystemInDarkTheme()
                },
            ) {
                SettingsScreen(appSettings)
            }
        }
    }
}

@Composable
fun SettingsScreen(settings: ThemeViewModel) {
    val context = LocalContext.current
    val theme by settings.themeMode.collectAsState(initial = ThemeMode.AUTO)

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text("Settings",
                        color = MaterialTheme.colorScheme.primary)
                },
                navigationIcon = {
                    IconButton(onClick = { (context as ComponentActivity).finish() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Filled.Save, "Save Settings")
            }
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
        ) {
            Text(
                text = "Tema",
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            SettingsItem(
                title = "Theme Mode",
                icon = Icons.Outlined.DarkMode orInLightTheme Icons.Outlined.LightMode,
                description = "This Mode will be applied to all the app",
                items = listOf("Follow System", "Light Mode", "Dark Mode"),
                onItemClick = {
                    settings.changeTheme(ThemeMode.values()[it])
                }
            )

            Text(
                text = "Bahasa",
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            SettingsItem(
                title = "Bahasa",
                icon = Icons.Filled.Language,
                description = "Pilih bahasa yang ingin digunakan",
                items = listOf("Indonesia", "English")
            )

            SettingsItem(
                title = "Bantu Terjemahkan",
                icon = Icons.Filled.Translate,
                description = "Bantu kami untuk menerjemahkan aplikasi ini"
            )

            Text(
                text = "Pembaruan",
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            SettingsItem(
                title = "Update",
                icon = Icons.Filled.Update,
                description = "Pilih versi pembaruan terbaru pada aplikasi",
                items = listOf("Stable", "Beta"),
//                onItemClick = {
//                    when (it) {
//                        0 -> {
//                            Util.isStable = true
//                        }
//                        1 -> {
//                            Util.isStable = false
//                        }
//                    }
//                }
            )
        }
    }
}

//@Preview(name = "Settings")
//@Composable
//fun SettingsPreview() {
//    TugasKuTheme {
//        SettingsScreen()
//    }
//}