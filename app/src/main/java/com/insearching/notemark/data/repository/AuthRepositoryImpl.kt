package com.insearching.notemark.data.repository

import com.insearching.notemark.data.remote.RemoteAuthDataSource
import com.insearching.notemark.data.remote.Response
import com.insearching.notemark.data.remote.mapSuccess
import com.insearching.notemark.data.session.TokenPair
import com.insearching.notemark.data.session.tokenPair
import com.insearching.notemark.domain.AuthRepository

class AuthRepositoryImpl(
    private val remoteAuthDataSource: RemoteAuthDataSource,
) : AuthRepository {

    override suspend fun register(
        username: String,
        email: String,
        password: String,
    ): Response<Unit> {
        return remoteAuthDataSource.register(username, email, password)
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Response<TokenPair> {
        val response = remoteAuthDataSource.login(email, password)
        return response.mapSuccess { tokenPair() }
    }
}