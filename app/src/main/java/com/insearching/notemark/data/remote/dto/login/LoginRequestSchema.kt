package com.insearching.notemark.data.remote.dto.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestSchema(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)