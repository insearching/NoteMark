package com.insearching.notemark.domain

import com.insearching.notemark.domain.model.UserToken

interface SessionStorage {
    suspend fun getUserToken(): UserToken?
    suspend fun updateToken(newTokens: UserToken)
}