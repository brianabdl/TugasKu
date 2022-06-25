package com.rewtio.tugasku.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
fun TugasCard(tugas: TugasData) {
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
                Box(
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    val items = listOf("Selesai", "Sedang Dikerjakan", "Belum Selesai")
                    var expanded by remember { mutableStateOf(false) }

                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(8.dp).clickable { expanded = true },
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        items.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    expanded = false
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
        status = Status.COMPLETED,
        judul = "Membuat Kerangka Bangunan",
        deskripsi = "Tugas untuk menghitung luas lingkaran",
        deadline = "20/10/2020",
        dibuat = "10/10/2020",
        mapel = "Matematika")
    TugasCard(data)
}