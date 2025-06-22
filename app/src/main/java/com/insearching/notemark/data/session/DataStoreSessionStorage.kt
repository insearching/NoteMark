package com.insearching.notemark.data.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.insearching.notemark.di.DispatchersProvider
import com.insearching.notemark.domain.SessionStorage
import com.insearching.notemark.domain.model.UserToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class DataStoreSessionStorage(
    private val context: Context,
    private val dispatchers: DispatchersProvider
) : SessionStorage {

    private val Context.dataStore by preferencesDataStore(name = "session_storage")
    private val tokenKey = stringPreferencesKey("token_pair")
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getUserToken(): UserToken? = withContext(dispatchers.io) {
        val preferences = context.dataStore.data.first()
        preferences[tokenKey]?.let { json.decodeFromString<UserToken>(it) }
    }

    override suspend fun updateToken(newTokens: UserToken) {
        withContext(dispatchers.io) {
            context.dataStore.edit { preferences ->
                preferences[tokenKey] = json.encodeToString(newTokens)
            }
        }
    }
}