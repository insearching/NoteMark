package com.insearching.notemark.data.mapper

import com.insearching.notemark.data.local.entity.NoteEntity
import com.insearching.notemark.data.remote.dto.note.NoteDto
import com.insearching.notemark.domain.model.Note
import java.util.UUID

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = this@toNote.content,
        createdAt = createdAt,
        lastEditedAt = lastEditedAt
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id ?: UUID.randomUUID().toString(),
        title = title,
        content = content,
        createdAt = createdAt,
        lastEditedAt = lastEditedAt
    )
}

fun NoteEntity.toDto(): NoteDto {
    return NoteDto(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        lastEditedAt = lastEditedAt,
    )
}

fun NoteDto.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = this@toNote.content,
        createdAt = createdAt,
        lastEditedAt = lastEditedAt
    )
}