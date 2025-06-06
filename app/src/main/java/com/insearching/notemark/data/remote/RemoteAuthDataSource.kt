package com.insearching.notemark.data.remote

import com.insearching.notemark.data.remote.dto.LoginRequestSchema
import com.insearching.notemark.data.remote.dto.LoginResponseSchema
import com.insearching.notemark.data.remote.dto.RegisterRequestSchema
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

const val BASE_URL = "https://notemark.pl-coding.com"

class RemoteAuthDataSource(
    private val client: HttpClient,
) {
    suspend fun register(username: String, email: String, password: String): Response<Unit> {
        val response = client.post("$BASE_URL/api/auth/register") {
            setBody(RegisterRequestSchema(username, email, password))
        }

        return response.toResult<Unit>()
    }

    suspend fun login(email: String, password: String): Response<LoginResponseSchema> {
        val response = client.post("$BASE_URL/api/auth/login") {
            setBody(LoginRequestSchema(email, password))
        }

        return response.toResult<LoginResponseSchema>()
    }
}