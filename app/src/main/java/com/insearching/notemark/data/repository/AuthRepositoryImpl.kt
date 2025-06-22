package com.insearching.notemark.data.repository

import com.insearching.notemark.data.remote.Response
import com.insearching.notemark.data.remote.datasource.RemoteAuthDataSource
import com.insearching.notemark.data.remote.mapSuccess
import com.insearching.notemark.domain.AuthRepository
import com.insearching.notemark.domain.SessionStorage
import com.insearching.notemark.domain.model.UserToken
import com.insearching.notemark.domain.model.userToken

class AuthRepositoryImpl(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val sessionStorage: SessionStorage,
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
    ): Response<UserToken> {
        val response = remoteAuthDataSource.login(email, password)
        return response.mapSuccess {
            userToken().also {
                sessionStorage.updateToken(it)
            }
        }
    }
}