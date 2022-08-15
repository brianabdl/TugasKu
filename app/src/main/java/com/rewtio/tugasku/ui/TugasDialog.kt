package com.rewtio.tugasku.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rewtio.tugasku.Status
import com.rewtio.tugasku.TugasData
import java.util.*

@Composable
fun TugasDialog(
    tugasData: TugasData? = null,
    onSave: (TugasData) -> Unit,
    onDismiss: () -> Unit,
) {
    var judul by remember { mutableStateOf("") }
    var mapel by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val yeari = calendar.get(Calendar.YEAR)
    val monthi = calendar.get(Calendar.MONTH)
    val dayi = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    if (tugasData != null) {
        judul = tugasData.judul
        mapel = tugasData.mapel
        deskripsi = tugasData.deskripsi
        deadline = tugasData.deadline
    }

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            deadline = "$dayOfMonth/$month/$year"
        }, yeari, monthi, dayi
    )

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "Detail Tugas")
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    label = { Text("Judul") },
                    singleLine = true,
                    value = judul,
                    onValueChange = { judul = it })

                OutlinedTextField(
                    label = { Text("Mapel") },
                    modifier = Modifier.padding(top = 8.dp),
                    singleLine = true,
                    value = mapel,
                    onValueChange = { mapel = it })

                OutlinedTextField(
                    label = { Text("Deskripsi") },
                    modifier = Modifier.padding(top = 8.dp),
                    value = deskripsi,
                    onValueChange = { deskripsi = it })

                Text(
                    text = "Deadline: $deadline",
                    modifier = Modifier.padding(top = 8.dp),
                )

                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { datePickerDialog.show() }
                ) {
                    Text(text = "Pilih Tanggal")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val data = if (tugasData != null) {
                        tugasData.status = Status.TODO
                        tugasData.judul = judul
                        tugasData.deskripsi = deskripsi
                        tugasData.deadline = deadline
                        tugasData.dibuat = "$dayi/$monthi/$yeari"
                        tugasData.mapel = mapel
                        tugasData
                    } else TugasData(
                        id = Random().nextInt().unaryPlus(),
                        status = Status.TODO,
                        judul = judul,
                        deskripsi = deskripsi,
                        deadline = deadline,
                        dibuat = "$dayi/$monthi/$yeari",
                        mapel = mapel
                    )

                    onSave(data)
                    onDismiss()
                }) {
                Icon(Icons.Filled.AddTask, "Add Task")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }) {
                Icon(Icons.Filled.Cancel, "Add Task")
            }
        }
    )
}

@Composable
fun EditTugasDialog(tugasData: TugasData, onSave: (TugasData) -> Unit, onDismiss: () -> Unit) {
    TugasDialog(tugasData, onSave, onDismiss)
}

@Composable
fun TambahTugasDialog(onAddTugas: (TugasData) -> Unit, onDismissRequest: () -> Unit) {
    TugasDialog(onSave = onAddTugas, onDismiss = onDismissRequest)
}

@Preview
@Composable
fun TambahTugasDialogPreview() {
    TambahTugasDialog(onAddTugas = {}, onDismissRequest = {})
}