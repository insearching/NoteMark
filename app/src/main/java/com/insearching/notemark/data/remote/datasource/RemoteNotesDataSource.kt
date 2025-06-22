package com.insearching.notemark.data.remote.datasource

import com.insearching.notemark.core.NetworkError
import com.insearching.notemark.core.Result
import com.insearching.notemark.core.map
import com.insearching.notemark.core.safeCall
import com.insearching.notemark.data.mapper.toNote
import com.insearching.notemark.data.remote.dto.note.NoteDto
import com.insearching.notemark.data.remote.dto.note.NoteListResponseSchema
import com.insearching.notemark.domain.model.Note
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class RemoteNoteDataSource(
    private val client: HttpClient,
) {
    suspend fun createNewNote(note: NoteDto): Result<Note, NetworkError> {
        return safeCall<NoteDto> {
            client.post("$BASE_URL/api/notes") {
                setBody(note)
            }
        }.map { it.toNote() }
    }

    suspend fun editNote(note: NoteDto): Result<Note, NetworkError> {
        return safeCall<NoteDto> {
            client.put("$BASE_URL/api/notes") {
                setBody(note)
            }
        }.map { it.toNote() }
    }

    suspend fun getNotes(page: Int, size: Int): Result<List<Note>, NetworkError> {
        return safeCall<NoteListResponseSchema> {
            client.get("$BASE_URL/api/notes") {
                parameter("page", page)
                parameter("size", size)
            }
        }.map { it.notes.map { it.toNote() } }
    }

    suspend fun deleteNote(id: String): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            client.delete("$BASE_URL/api/notes/$id")
        }
    }
}