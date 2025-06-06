package com.insearching.notemark.data.session

import com.insearching.notemark.data.remote.dto.LoginResponseSchema
import com.insearching.notemark.data.remote.dto.RefreshResponseSchema

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
)

fun RefreshResponseSchema.tokenPair(): TokenPair {
    return TokenPair(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}

fun LoginResponseSchema.tokenPair(): TokenPair {
    return TokenPair(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}