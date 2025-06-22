package com.insearching.notemark.domain.model

import java.time.ZonedDateTime

data class Note(
    val id: String? = null,
    val title: String,
    val content: String,
    val createdAt: ZonedDateTime,
    val lastEditedAt: ZonedDateTime,
)
