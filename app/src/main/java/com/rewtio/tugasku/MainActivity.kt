package com.rewtio.tugasku

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rewtio.tugasku.ui.TambahTugasDialog
import com.rewtio.tugasku.ui.TugasCard
import com.rewtio.tugasku.ui.theme.TugasKuTheme
import com.rewtio.tugasku.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TugasKuTheme {
                var openDialog by remember { mutableStateOf(false)  }
                if (openDialog) {
                    TambahTugasDialog(
                        onAddTugas = {
                            vm.addTugas(it)
                            openDialog = false
                        },
                        onDismissRequest = { openDialog = false })
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    style = TextStyle(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                )
                            },
                            actions = {
                                IconButton(
                                    onClick = { openDialog = true }) {
                                    Icon(Icons.Filled.Settings, "Open Setting Panel")
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { openDialog = true },
                            containerColor = MaterialTheme.colorScheme.secondary
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