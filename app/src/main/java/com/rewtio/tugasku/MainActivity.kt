package com.rewtio.tugasku

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.preferencesDataStore
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rewtio.tugasku.ui.TambahTugasDialog
import com.rewtio.tugasku.ui.TugasCard
import com.rewtio.tugasku.ui.theme.TugasKuTheme
import com.rewtio.tugasku.ui.viewmodel.MainViewModel
import com.rewtio.tugasku.preferences.ThemeMode
import com.rewtio.tugasku.ui.theme.titleHeadColor
import com.rewtio.tugasku.ui.viewmodel.ThemeViewModel

val Context.dataStore by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

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
                var openDialog by remember { mutableStateOf(false) }
                if (openDialog) {
                    TambahTugasDialog(
                        onAddTugas = { vm.addTugas(it) },
                        onDismissRequest = { openDialog = false })
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        SmallTopAppBar(
                            title = {
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    color = MaterialTheme.colorScheme.primary,
                                    style = TextStyle(
                                        fontFamily = FontFamily.Default,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                    )
                                )
                            },
                            actions = {
                                IconButton(
                                    onClick = {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                SettingsActivity::class.java
                                            )
                                        )
                                    }
                                ) {
                                    Icon(Icons.Filled.Settings, "Settings")
                                }
                            })
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { openDialog = true }
                        ) {
                            Icon(Icons.Filled.Add, "Tambah Tugas")
                        }
                    }
                ) { pad ->
                    val isRefreshing by vm.isRefreshing.collectAsState(initial = false)

                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pad),
                        onRefresh = { vm.onRefresh() }
                    ) {
                        Column {
                            val list = vm.listTugas.collectAsState(initial = emptyList())
                            list.value.forEach {
                                TugasCard(it)
                            }
                        }
                    }
                }
            }
        }
    }
}