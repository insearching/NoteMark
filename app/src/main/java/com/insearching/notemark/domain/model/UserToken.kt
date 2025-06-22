package com.insearching.notemark.domain.model

import com.insearching.notemark.data.remote.dto.login.LoginResponseSchema
import com.insearching.notemark.data.remote.dto.refresh_token.RefreshResponseSchema
import kotlinx.serialization.Serializable

@Serializable
data class UserToken(
    val accessToken: String,
    val refreshToken: String,
    val username: String
)

fun RefreshResponseSchema.userToken(userName: String): UserToken {
    return UserToken(
        accessToken = accessToken,
        refreshToken = refreshToken,
        username = userName
    )
}

fun LoginResponseSchema.userToken(): UserToken {
    return UserToken(
        accessToken = accessToken,
        refreshToken = refreshToken,
        username = username
    )
}