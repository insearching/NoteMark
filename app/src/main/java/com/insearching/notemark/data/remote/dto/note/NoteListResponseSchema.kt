package com.insearching.notemark.data.remote.dto.note

import kotlinx.serialization.SerialName

data class NoteListResponseSchema(
    @SerialName("id")
    val notes: List<NoteDto>,
    @SerialName("total")
    val total: Long
)
