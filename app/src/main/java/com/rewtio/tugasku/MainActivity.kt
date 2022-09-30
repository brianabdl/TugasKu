package com.rewtio.tugasku

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rewtio.tugasku.database.TugasData
import com.rewtio.tugasku.database.TugasDatabase
import com.rewtio.tugasku.preferences.AppSettings
import com.rewtio.tugasku.ui.EditTugasDialog
import com.rewtio.tugasku.ui.TambahTugasDialog
import com.rewtio.tugasku.ui.TugasCard
import com.rewtio.tugasku.ui.theme.TugasKuTheme
import com.rewtio.tugasku.ui.viewmodel.MainViewModel
import com.rewtio.tugasku.utils.LocaleUtils

val Context.dataStore by preferencesDataStore(name = "${BuildConfig.APPLICATION_ID}_preferences")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            AppSettings.instance.fetchSettings(this@MainActivity)
        }

        setContent {
            val context = LocalContext.current
            val vm: MainViewModel = viewModel()

            TugasKuTheme {
                val language by AppSettings.instance.localeState.collectAsState()
                LocaleUtils.SetLanguage(language)

                var openTambahDialog by remember { mutableStateOf(false) }
                var openEditDialog by remember { mutableStateOf(false) }
                var curTugasData by remember { mutableStateOf(TugasData()) }

                AnimatedVisibility(
                    visible = openTambahDialog,
                    exit = ExitTransition.None
                ) {
                    TambahTugasDialog(
                        onAddTugas = { vm.addTugas(it) },
                        onDismissRequest = { openTambahDialog = false })
                }

                AnimatedVisibility(
                    visible = openEditDialog,
                    exit = ExitTransition.None
                ) {
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
                                Text(text = stringResource(R.string.app_name))
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
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { openTambahDialog = true }
                        ) {
                            Icon(Icons.Filled.Add, "Tambah Tugas")
                        }
                    }
                ) { pad ->
                    val listTugas by vm.listTugas.collectAsState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pad)
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