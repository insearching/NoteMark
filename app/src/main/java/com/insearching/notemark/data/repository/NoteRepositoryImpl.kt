package com.insearching.notemark.data.repository

import com.insearching.notemark.core.onError
import com.insearching.notemark.core.onSuccess
import com.insearching.notemark.data.local.dao.NoteDao
import com.insearching.notemark.data.local.entity.NoteEntity
import com.insearching.notemark.data.mapper.toDto
import com.insearching.notemark.data.mapper.toEntity
import com.insearching.notemark.data.mapper.toNote
import com.insearching.notemark.data.remote.datasource.RemoteNoteDataSource
import com.insearching.notemark.domain.NoteRepository
import com.insearching.notemark.domain.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.LinkedList
import kotlin.time.Duration.Companion.seconds

private const val PAGE_SIZE = 20

class NoteRepositoryImpl(
    private val localDataSource: NoteDao,
    private val remoteDataSource: RemoteNoteDataSource,
    private val appScope: CoroutineScope,
) : NoteRepository {

    private val failedOperations = LinkedList<FailedOperation>()

    init {
        runFailedOperations()
    }

    private fun runFailedOperations() {
        appScope.launch {
            val operation = failedOperations.peek()
            while (operation != null) {
                retryOperation(operation)
                delay(10.seconds)
            }
        }
    }

    private suspend fun retryOperation(operation: FailedOperation) {
        when (operation.operationType) {
            OperationType.Add -> {
                remoteDataSource.createNewNote(operation.note.toDto())
                    .onSuccess {
                        Timber.i("Note added successfully")
                        failedOperations.poll()
                    }
                    .onError {
                        Timber.i("Failed to save note on server")
                    }
            }

            OperationType.Edit -> {
                remoteDataSource.editNote(operation.note.toDto())
                    .onSuccess {
                        Timber.i("Note edit successfully")
                        failedOperations.poll()
                    }
                    .onError {
                        Timber.i("Failed to edit note on server")
                    }
            }

            OperationType.Delete -> {
                remoteDataSource.deleteNote(operation.note.id)
                    .onSuccess {
                        Timber.i("Note deleted successfully")
                        failedOperations.poll()
                    }
                    .onError {
                        Timber.i("Failed to delete note from server")
                    }
            }
        }
    }


    override fun getAllNotes(): Flow<List<Note>> {
        return flow {
            emitAll(
                localDataSource.getAllNotes().map {
                    it.map { it.toNote() }
                }
            )

            remoteDataSource.getNotes(0, PAGE_SIZE)
                .onSuccess { notes ->
                    localDataSource.upsertAll(notes = notes.map { it.toEntity() })
                }
                .onError {
                    Timber.i("Failed to load fresh notes")
                }
        }
    }

    override suspend fun addNote(note: Note) {
        val entity = note.toEntity()
        localDataSource.upsert(entity)

        appScope.launch {
            remoteDataSource.createNewNote(entity.toDto())
                .onSuccess {
                    Timber.i("Note added successfully")
                }
                .onError {
                    failedOperations.add(FailedOperation(OperationType.Add, entity))
                    Timber.i("Failed to save note on server")
                }
        }
    }

    override suspend fun editNote(note: Note) {
        val entity = note.toEntity()
        localDataSource.upsert(entity)

        appScope.launch {
            remoteDataSource.editNote(entity.toDto())
                .onSuccess {
                    Timber.i("Note edit successfully")
                }
                .onError {
                    failedOperations.add(FailedOperation(OperationType.Edit, entity))
                    Timber.i("Failed to edit note on server")
                }
        }
    }

    override suspend fun deleteNote(note: Note): Boolean {
        val id = note.id ?: ""
        val result = localDataSource.delete(id) != 0

        remoteDataSource.deleteNote(id)
            .onSuccess {
                Timber.i("Note deleted successfully")
            }
            .onError {
                failedOperations.add(FailedOperation(OperationType.Delete, note.toEntity()))
                Timber.i("Failed to delete note from server")
            }

        return result
    }

    override suspend fun getNoteById(id: String): Note {
        return localDataSource.getNoteById(id).toNote()
    }
}

data class FailedOperation(
    val operationType: OperationType,
    val note: NoteEntity,
)

enum class OperationType {
    Add,
    Edit,
    Delete
}