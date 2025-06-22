package com.insearching.notemark.presentation.screens.note_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.insearching.notemark.domain.NoteRepository
import com.insearching.notemark.domain.model.Note
import com.insearching.notemark.presentation.navigation.Route.NoteDetailsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class NoteDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository,
    private val appScope: CoroutineScope,
) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailsState.Initial)
    val state = _state.asStateFlow()

    private val _events = Channel<NoteDetailsEvent>()
    val events = _events.receiveAsFlow()

    private var editedNote: Note? = null

    private var noteChanged: Boolean = false

    init {
        savedStateHandle.toRoute<NoteDetailsScreen>().id?.let {
            viewModelScope.launch {
                editedNote = noteRepository.getNoteById(it)
                _state.update {
                    it.copy(
                        title = editedNote?.title ?: "",
                        content = editedNote?.content ?: ""
                    )
                }
            }
        }
    }

    fun onAction(action: NoteDetailsAction) {
        when (action) {
            is NoteDetailsAction.OnTitleChanged -> {
                _state.update {
                    it.copy(title = action.title)
                }
                noteChanged = true
            }

            is NoteDetailsAction.OnContentChanged -> {
                _state.update {
                    it.copy(content = action.content)
                }
                noteChanged = true
            }

            NoteDetailsAction.OnSaveNoteClicked -> {
                saveNote()
            }

            NoteDetailsAction.OnDiscardNoteClicked -> {
                viewModelScope.launch {
                    if (noteChanged) {
                        _events.trySend(NoteDetailsEvent.OnDiscardEvent)
                    } else {
                        _events.trySend(NoteDetailsEvent.OnBack)
                    }
                }
            }
        }
    }

    private fun saveNote() {
        val title = _state.value.title
        val text = _state.value.content

        if (title.isNotBlank() && text.isNotBlank()) {
            viewModelScope.launch {
                editedNote?.let { note ->
                    editNote(title, text, note)
                } ?: run {
                    addNote(title, text)
                }
            }
        }
    }

    private suspend fun addNote(title: String, text: String) {
        noteRepository.addNote(
            Note(
                title = title,
                content = text,
                createdAt = ZonedDateTime.now(),
                lastEditedAt = ZonedDateTime.now()
            )
        )
        _events.trySend(NoteDetailsEvent.OnNoteSaved)
    }

    private suspend fun editNote(title: String, text: String, oldNote: Note) {
        noteRepository.editNote(
            Note(
                id = oldNote.id,
                title = title,
                content = text,
                createdAt = oldNote.createdAt,
                lastEditedAt = ZonedDateTime.now()
            )
        )
        _events.trySend(NoteDetailsEvent.OnNoteSaved)
    }
}