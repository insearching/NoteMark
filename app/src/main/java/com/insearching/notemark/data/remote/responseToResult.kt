package com.insearching.notemark.data.remote

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T : Any> HttpResponse.toResult(): Response<T> {
    return when (status.value) {
        200 -> Response.Success(body())
        400 -> Response.Error(NetworkException("Check your credentials and try again!"))
        401 -> Response.Error(NetworkException("Authorization Failed! Try Logging In again."))
        409 -> Response.Error(NetworkException("A user with that email or username already exists."))
        500, 503 -> Response.Error(NetworkException("Server Disruption! We are on fixing it."))
        504 -> Response.Error(NetworkException("Too much load at this time, try again later!"))
        else -> Response.Error(NetworkException("Something went wrong! Please try again or contact support."))
    }
}

class NetworkException(message: String) : Exception(message)