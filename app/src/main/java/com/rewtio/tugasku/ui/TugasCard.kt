package com.rewtio.tugasku.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rewtio.tugasku.database.Status
import com.rewtio.tugasku.database.TugasData

@Composable
fun TugasCard(
    modifier: Modifier, tugas: TugasData, onDelete: (TugasData) -> Unit, onEdit: (TugasData) -> Unit
) {

    var expandMenu by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = tugas.judul,
                    modifier = Modifier.align(Alignment.CenterVertically).weight(1F),
                    style = MaterialTheme.typography.titleLarge
                )

                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = { expandMenu = !expandMenu }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "Edit Menu"
                    )

                    DropdownMenu(
                        expanded = expandMenu,
                        onDismissRequest = { expandMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = {
                                onEdit(tugas.copy())
                                expandMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                onDelete(tugas)
                                expandMenu = false
                            }
                        )
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
    TugasCard(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        data, {}, {})
}