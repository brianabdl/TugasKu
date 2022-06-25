package com.rewtio.tugasku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rewtio.tugasku.ui.theme.TugasKuTheme
import com.rewtio.tugasku.ui.viewmodel.SettingsViewModel

class SettingsActivity : ComponentActivity() {

    private val vm: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TugasKuTheme {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text("Settings")
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Close, "Close")
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        imageVector = Icons.Filled.DarkMode,
                        contentDescription = "Dark Mode",
                        modifier = Modifier
                            .width(38.dp)
                            .height(38.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Dark Mode",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "This Mode will be applied to all the app",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Checkbox(checked = false, onCheckedChange = {

                    })
                }
            }

            Text("Bahasa", modifier = Modifier.padding(start = 16.dp, top = 16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Language,
                        contentDescription = "Language",
                        modifier = Modifier
                            .width(38.dp)
                            .height(38.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Bahasa",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Pilih bahasa yang ingin digunakan",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Card(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Translate,
                        contentDescription = "Translate",
                        modifier = Modifier
                            .width(38.dp)
                            .height(38.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Bantu Terjemahkan",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Bantu kami untuk menerjemahkan aplikasi ini",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Text("Pembaruan", modifier = Modifier.padding(start = 16.dp, top = 16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Update,
                        contentDescription = "Update",
                        modifier = Modifier
                            .width(38.dp)
                            .height(38.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Pembaruan",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Pilih versi pembaruan terbaru pada aplikasi",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Settings")
@Composable
fun SettingsPreview() {
    TugasKuTheme {
        SettingsScreen()
    }
}