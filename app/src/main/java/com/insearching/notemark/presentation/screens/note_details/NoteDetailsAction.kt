package com.insearching.notemark.presentation.screens.note_details

sealed interface NoteDetailsAction {
    data class OnTitleChanged(val title: String) : NoteDetailsAction
    data class OnContentChanged(val content: String) : NoteDetailsAction
    data object OnSaveNoteClicked : NoteDetailsAction
    data object OnDiscardNoteClicked : NoteDetailsAction
}