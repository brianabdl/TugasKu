package com.rewtio.tugasku

enum class Status {
    COMPLETED,
    NOT_COMPLETED,
    IN_PROGRESS,
    TODO
}

data class TugasData(
    val id: Int,
    var status: Status,
    var judul: String,
    var mapel: String,
    var deskripsi: String,
    var dibuat: String,
    var deadline: String,
) {
    constructor() : this(-1, Status.TODO, "", "", "", "", "")
}