package com.rewtio.tugasku.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rewtio.tugasku.database.Status
import com.rewtio.tugasku.database.TugasData

@Composable
fun TugasCard(tugas: TugasData, onDelete: (TugasData) -> Unit, onEdit: (TugasData) -> Unit) {

    val itemMenu = remember { listOf("Edit", "Delete") }
    var expandMenu by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ){
                Text(
                    text = tugas.judul,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1F),
                    style = MaterialTheme.typography.titleLarge
                )

                IconButton(onClick = { expandMenu = !expandMenu }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "Edit Menu"
                    )

                    DropdownMenu(
                        expanded = expandMenu,
                        onDismissRequest = { expandMenu = false }
                    ) {
                        itemMenu.forEachIndexed { index, str ->
                            DropdownMenuItem(
                                text = { Text(str) },
                                onClick = {
                                    when (index) {
                                        0 -> {
                                            onEdit(tugas)
                                        }
                                        1 -> {
                                            onDelete(tugas)
                                        }
                                    }
                                    expandMenu = false
                                }
                            )
                        }
                    }
                }
            }
            Text(
                text = "Mapel : ${tugas.mapel}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Dibuat : ${tugas.dibuat}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Deadline : ${tugas.deadline}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Rincian : ${tugas.deskripsi}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun TugasCardPreview() {
    val data = TugasData(
        -1,
        status = Status.COMPLETED,
        judul = "Membuat Kerangka Bangunan",
        deskripsi = "Tugas untuk menghitung luas lingkaran",
        deadline = "20/10/2020",
        dibuat = "10/10/2020",
        mapel = "Matematika"
    )
    TugasCard(data, {}, {})
}