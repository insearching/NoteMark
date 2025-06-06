package com.insearching.notemark.data.remote


sealed interface Response<out T : Any> {
    data class Success<out T : Any>(val data: T) : Response<T>
    data class Error(val error: Exception) : Response<Nothing>
}

inline fun <T : Any, R : Any> Response<T>.mapSuccess(transform: T.() -> R): Response<R> {
    return when (this) {
        is Response.Success -> Response.Success(data.transform())
        is Response.Error -> this
    }
}