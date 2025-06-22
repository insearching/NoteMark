package com.insearching.notemark.domain

import com.insearching.notemark.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun editNote(note: Note)
    suspend fun deleteNote(note: Note): Boolean
    suspend fun getNoteById(id: String): Note
}