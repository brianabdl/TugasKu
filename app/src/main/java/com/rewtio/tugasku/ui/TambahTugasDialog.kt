package com.rewtio.tugasku.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            deadline = "$dayOfMonth/$month/$year"
        }, year, month, day
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
                    text = "Deadline: ${deadline}",
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
                        status = Status.COMPLETED,
                        judul = judul,
                        deskripsi = deskripsi,
                        deadline = deadline,
                        dibuat = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)}/${calendar.get(Calendar.YEAR)}",
                        mapel = "Matematika"
                    )
                    onAddTugas(data)
                    onDismissRequest()
                }) {
                Text("Tambah Tugas")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun TambahTugasDialogPreview() {
    TambahTugasDialog(onAddTugas = {}, onDismissRequest = {})
}