package com.rewtio.tugasku.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.rewtio.tugasku.Status
import com.rewtio.tugasku.TugasData

@Composable
fun TambahTugasDialog(
    onAddTugas: (TugasData) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var judul = ""
    var deskripsi = ""
    var status = Status.TODO
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text(text = "Isi Detail Tugas")
        },
        text = {
            Column {
                TextField(
                    label = { Text("Judul") },
                    value = judul,
                    onValueChange = { judul = it })
                TextField(
                    label = { Text("Deskripsi") },
                    value = deskripsi,
                    onValueChange = { deskripsi = it })
                Row {
                    var expanded by remember { mutableStateOf(false) }
                    Text("Status")
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        for (s in Status.values()) {
                            DropdownMenuItem(
                                text = { Text(s.name) },
                                onClick = { status = s }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val data = TugasData(
                        status = Status.COMPLETED,
                        judul = "Membuat Kerangka Bangunan",
                        deskripsi = "Tugas untuk menghitung luas lingkaran",
                        deadline = "20/10/2020",
                        dibuat = "10/10/2020",
                        mapel = "Matematika")
                    onAddTugas(data)
                    onDismissRequest()
                }) {
                Text("This is the Confirm Button")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }) {
                Text("This is the dismiss Button")
            }
        }
    )
}

@Preview
@Composable
fun TambahTugasDialogPreview() {
    TambahTugasDialog(onAddTugas = {}, onDismissRequest = {})
}