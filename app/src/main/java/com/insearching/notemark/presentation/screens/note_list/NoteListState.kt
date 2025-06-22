package com.insearching.notemark.presentation.screens.note_list

import com.insearching.notemark.domain.model.Note

sealed class NoteListState {
    data object Initial : NoteListState()
    data class Success(
        val notes: List<Note>,
        val userName: String,
    ) : NoteListState()
}