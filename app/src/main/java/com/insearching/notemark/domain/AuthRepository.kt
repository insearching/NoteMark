package com.insearching.notemark.domain

import com.insearching.notemark.data.remote.Response
import com.insearching.notemark.data.session.TokenPair

interface AuthRepository {
    suspend fun register(username: String, email: String, password: String): Response<Unit>

    suspend fun login(email: String, password: String): Response<TokenPair>
}