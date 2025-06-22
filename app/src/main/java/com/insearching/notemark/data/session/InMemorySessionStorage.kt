package com.insearching.notemark.data.session

import com.insearching.notemark.domain.SessionStorage
import com.insearching.notemark.domain.model.UserToken

class InMemorySessionStorage : SessionStorage {
    private var userToken: UserToken? = null

    override suspend fun getUserToken(): UserToken? = userToken

    override suspend fun updateToken(newTokens: UserToken) {
        userToken = newTokens
    }
}