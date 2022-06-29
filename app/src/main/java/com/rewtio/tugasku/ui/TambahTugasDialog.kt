package com.rewtio.tugasku.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
fun TambahTugasDialog(
    onAddTugas: (TugasData) -> Unit,
    onDismissRequest: () -> Unit,
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

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            deadline = "$dayOfMonth/$month/$year"
        }, yeari, monthi, dayi
    )

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
                    singleLine = true,
                    value = judul,
                    onValueChange = { judul = it })

                TextField(
                    label = { Text("Mapel") },
                    modifier = Modifier.padding(top = 8.dp),
                    singleLine = true,
                    value = mapel,
                    onValueChange = { mapel = it })

                TextField(
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
                    onClick = {
                        datePickerDialog.show()
                    }
                ) {
                    Text(text = "Pilih Tanggal")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val data = TugasData(
                        status = Status.TODO,
                        judul = judul,
                        deskripsi = deskripsi,
                        deadline = deadline,
                        dibuat = "$dayi/$monthi/$yeari",
                        mapel = mapel
                    )
                    onAddTugas(data)
                    onDismissRequest()
                }) {
                Icon(Icons.Filled.AddTask, "Add Task")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }) {
                Icon(Icons.Filled.Cancel, "Add Task")
            }
        }
    )
}

@Preview
@Composable
fun TambahTugasDialogPreview() {
    TambahTugasDialog(onAddTugas = {}, onDismissRequest = {})
}