package com.insearching.notemark.presentation.screens.note_details

interface NoteDetailsEvent {
    data object OnNoteSaved : NoteDetailsEvent
    data object OnDiscardEvent : NoteDetailsEvent
    data object OnBack : NoteDetailsEvent
}