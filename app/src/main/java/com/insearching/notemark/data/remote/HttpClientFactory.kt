package com.insearching.notemark.data.remote

import com.insearching.notemark.BuildConfig
import com.insearching.notemark.data.remote.dto.RefreshRequestSchema
import com.insearching.notemark.data.remote.dto.RefreshResponseSchema
import com.insearching.notemark.data.session.SessionStorage
import com.insearching.notemark.data.session.tokenPair
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

object HttpClientFactory {

    fun create(
        engine: HttpClientEngine,
        sessionStorage: SessionStorage,
    ): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(DefaultRequest) {
                header("X-User-Email", BuildConfig.X_USER_EMAIL)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val tokenPair = sessionStorage.get()
                        BearerTokens(
                            accessToken = tokenPair?.accessToken ?: "",
                            refreshToken = tokenPair?.refreshToken ?: ""
                        )
                    }

                    refreshTokens {
                        // 1. Retrieve current pair of tokens
                        val tokenPair = sessionStorage.get()

                        // 2. Use refresh token to get new access token
                        val response =
                            client.post("$BASE_URL/api/auth/refresh") {
                                contentType(ContentType.Application.Json)
                                setBody(
                                    RefreshRequestSchema(
                                        refreshToken = tokenPair?.refreshToken ?: ""
                                    )
                                )
                                markAsRefreshTokenRequest()

                                // Add this, if you want to test your refresh mechanism.
                                // This makes tokens valid for only 30s.
                                header("Debug", true)
                            }.toResult<RefreshResponseSchema>()

                        if (response is Response.Success) {
                            // 3. Update session storage with new pair of tokens
                            val newAuthInfo = response.data
                            sessionStorage.update(newAuthInfo.tokenPair())

                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken
                            )
                        } else {
                            BearerTokens(
                                accessToken = "",
                                refreshToken = ""
                            )
                        }
                    }
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        if (BuildConfig.DEBUG) {
                            Timber.tag("Ktor").i(message)
                        } else {
                            if (message.startsWith("REQUEST:") || message.startsWith("METHOD:")) {
                                Timber.tag("Ktor").i(message)
                            }
                        }
                    }
                }

                level = if (BuildConfig.DEBUG) {
                    LogLevel.ALL
                } else {
                    LogLevel.INFO // minimal, just request basics
                }
            }
        }
    }
}