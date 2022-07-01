package com.rewtio.tugasku.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rewtio.tugasku.Status
import com.rewtio.tugasku.TugasData

@Composable
fun TugasCard(tugas: TugasData, onDelete: (TugasData) -> Unit, onEdit: (TugasData) -> Unit) {
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
            ) {
                Text(
                    text = tugas.judul,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.weight(1F),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineSmall,
                )

                val itemMenu = listOf("Edit", "Delete")
                var expandMenu by remember { mutableStateOf(false) }

                Box(
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Icon(
                        Icons.Filled.MoreVert,
                        modifier = Modifier.clickable { expandMenu = !expandMenu },
                        contentDescription = "Edit Menu"
                    )

                    DropdownMenu(
                        expanded = expandMenu,
                        onDismissRequest = { expandMenu = false }
                    ) {
                        itemMenu.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    when (it) {
                                        "Edit" -> onEdit(tugas)
                                        "Delete" -> onDelete(tugas)
                                    }
                                    expandMenu = false
                                })
                        }
                    }
                }
            }
            Text(
                text = "Mapel : ${tugas.mapel}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Dibuat : ${tugas.dibuat}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Deadline : ${tugas.deadline}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Deskripsi : ${tugas.deskripsi}",
                style = MaterialTheme.typography.bodySmall
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
        mapel = "Matematika")
    TugasCard(data, {}, {})
}