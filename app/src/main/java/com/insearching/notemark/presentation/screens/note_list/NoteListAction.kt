package com.insearching.notemark.presentation.screens.note_list

import com.insearching.notemark.domain.model.Note

sealed interface NoteListAction {
    data object OnNoteAdd : NoteListAction
    data class OnNoteEdit(val note: Note) : NoteListAction
    data class OnNoteDelete(val note: Note) : NoteListAction
}