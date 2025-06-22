package com.insearching.notemark.data.remote.datasource

import com.insearching.notemark.data.remote.Response
import com.insearching.notemark.data.remote.dto.login.LoginRequestSchema
import com.insearching.notemark.data.remote.dto.login.LoginResponseSchema
import com.insearching.notemark.data.remote.dto.RegisterRequestSchema
import com.insearching.notemark.data.remote.toResult
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

const val BASE_URL = "https://notemark.pl-coding.com"

class RemoteAuthDataSource(
    private val client: HttpClient,
) {
    suspend fun register(username: String, email: String, password: String): Response<Unit> {
        return try {
            val response = client.post("$BASE_URL/api/auth/register") {
                setBody(RegisterRequestSchema(username, email, password))
            }
            response.toResult<Unit>()
        } catch (ex: Exception) {
            Response.Error(ex)
        }
    }

    suspend fun login(email: String, password: String): Response<LoginResponseSchema> {
        return try {
            val response = client.post("$BASE_URL/api/auth/login") {
                setBody(LoginRequestSchema(email, password))
            }
            response.toResult<LoginResponseSchema>()
        } catch (ex: Exception) {
            Response.Error(ex)
        }
    }
}