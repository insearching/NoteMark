package com.insearching.notemark.data.remote.dto.refresh_token

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestSchema(
    @SerialName("refreshToken")
    val refreshToken: String
)