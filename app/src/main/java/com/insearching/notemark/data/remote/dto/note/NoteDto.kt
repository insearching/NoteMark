package com.insearching.notemark.data.remote.dto.note

import com.insearching.notemark.data.remote.converter.ZonedDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Serializable
data class NoteDto (
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("createdAt")
    @Serializable(with = ZonedDateTimeSerializer::class)
    val createdAt: ZonedDateTime,
    @SerialName("lastEditedAt")
    @Serializable(with = ZonedDateTimeSerializer::class)
    val lastEditedAt: ZonedDateTime,
)