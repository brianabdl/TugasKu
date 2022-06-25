package com.rewtio.tugasku

enum class Status {
    COMPLETED,
    NOT_COMPLETED,
    IN_PROGRESS,
    TODO
}

data class TugasData(
    val status: Status,
    val judul: String,
    val mapel: String,
    val deskripsi: String,
    val dibuat: String,
    val deadline: String,
)