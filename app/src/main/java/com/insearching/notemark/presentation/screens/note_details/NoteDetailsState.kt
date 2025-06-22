package com.insearching.notemark.presentation.screens.note_details

data class NoteDetailsState(
    val title: String,
    val content: String
) {
    companion object {
        val Initial = NoteDetailsState(
            title = "",
            content = ""
        )
    }
}
