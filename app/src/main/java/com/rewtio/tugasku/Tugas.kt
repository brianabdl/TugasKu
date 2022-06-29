package com.rewtio.tugasku

enum class Status {
    COMPLETED,
    NOT_COMPLETED,
    IN_PROGRESS,
    TODO
}

data class TugasData(
    var status: Status,
    var judul: String,
    var mapel: String,
    var deskripsi: String,
    var dibuat: String,
    var deadline: String,
) {
    fun isMatch(other: TugasData): Boolean {
        return this.status == status && this.judul == other.judul && this.mapel == other.mapel && this.deskripsi == other.deskripsi && this.dibuat == other.dibuat && this.deadline == other.deadline
    }
}