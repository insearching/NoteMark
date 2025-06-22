package com.insearching.notemark.presentation.screens.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insearching.notemark.domain.NoteRepository
import com.insearching.notemark.domain.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class NoteListViewModel(
    private val noteRepository: NoteRepository,
    private val sessionStorage: SessionStorage,
) : ViewModel() {

    private val _state = MutableStateFlow<NoteListState>(NoteListState.Initial)
    val state = _state
        .onStart { loadData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), NoteListState.Initial)

    private suspend fun loadData() {
        val userToken = sessionStorage.getUserToken()
        noteRepository.getAllNotes()
            .map { notes ->
                if (notes.isEmpty()) {
                    _state.update { NoteListState.Initial }
                } else {
                    _state.update { NoteListState.Success(notes, userToken?.username?.toInitials() ?: "") }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: NoteListAction) {
        when (action) {
            is NoteListAction.OnNoteDelete -> {
                viewModelScope.launch {
                    Timber.i("Note delete - ${action.note}")
                    if (noteRepository.deleteNote(action.note)) {
                        Timber.i("Note ${action.note} deleted")
                    } else {
                        Timber.e("Failed to delete note")
                    }
                }
            }

            else -> {
                // do nothing
            }
        }
    }

    fun String.toInitials(): String {
        val words = this.trim().split("\\s+".toRegex())
        return when {
            words.size >= 2 -> (words[0].firstOrNull()?.toString() + words[1].firstOrNull()?.toString())
            words.size == 1 -> words[0].take(2)
            else -> ""
        }.uppercase()
    }

}