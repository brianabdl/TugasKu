package com.rewtio.tugasku

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rewtio.tugasku.preferences.ThemeMode
import com.rewtio.tugasku.ui.EditTugasDialog
import com.rewtio.tugasku.ui.TambahTugasDialog
import com.rewtio.tugasku.ui.TugasCard
import com.rewtio.tugasku.ui.theme.TugasKuTheme
import com.rewtio.tugasku.ui.viewmodel.MainViewModel
import com.rewtio.tugasku.ui.viewmodel.ThemeViewModel
import kotlinx.coroutines.delay

val Context.dataStore by preferencesDataStore(name = "${BuildConfig.APPLICATION_ID}_preferences")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val vm: MainViewModel = viewModel()
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
                var openTambahDialog by remember { mutableStateOf(false) }
                if (openTambahDialog) {
                    TambahTugasDialog(
                        onAddTugas = { vm.addTugas(it) },
                        onDismissRequest = { openTambahDialog = false })
                }

                var openEditDialog by remember { mutableStateOf(false) }
                var curTugasData by remember {
                    mutableStateOf(TugasData(Status.TODO, "", "", "", "", ""))
                }

                if (openEditDialog) {
                    EditTugasDialog(
                        curTugasData,
                        onSave = { vm.editTugas(it) },
                        onDismiss = { openEditDialog = false })
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(id = R.string.app_name),
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
                            onClick = { openTambahDialog = true }
                        ) {
                            Icon(Icons.Filled.Add, "Tambah Tugas")
                        }
                    }
                ) { pad ->
                    var isRefreshing by remember { vm.isRefreshing }
                    val listTugas = remember { vm.listTugas }

                    LaunchedEffect(isRefreshing) {
                        if (isRefreshing) {
                            delay(1000)
                            vm.onRefresh()
                            isRefreshing = false
                        }
                    }

                    SwipeRefresh(
                        swipeEnabled = true,
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pad),
                        onRefresh = {
                            isRefreshing = true
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            listTugas.forEach { tugasData ->
                                TugasCard(tugasData,
                                    onDelete = { vm.deleteTugas(it) },
                                    onEdit = {
                                        curTugasData = tugasData
                                        openEditDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}