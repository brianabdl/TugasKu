package com.rewtio.tugasku.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Status {
    COMPLETED,
    NOT_COMPLETED,
    IN_PROGRESS,
    TODO
}

@Entity(tableName = "tugas")
data class TugasData(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    var status: Status,
    var judul: String,
    var mapel: String,
    var deskripsi: String,
    var dibuat: String,
    var deadline: String,
) {
    constructor() : this(-1, Status.TODO, "", "", "", "", "")
}