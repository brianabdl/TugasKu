package com.rewtio.tugasku

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rewtio.tugasku.database.TugasData
import com.rewtio.tugasku.preferences.AppSettings
import com.rewtio.tugasku.ui.EditTugasDialog
import com.rewtio.tugasku.ui.TambahTugasDialog
import com.rewtio.tugasku.ui.TugasCard
import com.rewtio.tugasku.ui.theme.TugasKuTheme
import com.rewtio.tugasku.ui.viewmodel.MainViewModel
import com.rewtio.tugasku.utils.LocaleUtils

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val vm: MainViewModel = viewModel(factory = viewModelFactory {
                initializer { MainViewModel((application as TugasKuApplication).database) }
            })

            TugasKuTheme {
                val language by AppSettings.localeState.collectAsState()
                LocaleUtils.SetLanguage(language)

                var openTambahDialog by remember { mutableStateOf(false) }
                var openEditDialog by remember { mutableStateOf(false) }
                var curTugasData by remember { mutableStateOf(TugasData()) }

                if (openTambahDialog) {
                    TambahTugasDialog(
                        onAddTugas = { vm.addTugas(it) },
                        onDismissRequest = { openTambahDialog = false })
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

                    val listTugas by vm.listTugas.collectAsState(initial = emptyList())

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pad)
                    ) {
                        items(listTugas, key = { it.id!! }) { tugasData ->
                            TugasCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                tugasData,
                                onDelete = { vm.deleteTugas(it) },
                                onEdit = {
                                    curTugasData = it
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