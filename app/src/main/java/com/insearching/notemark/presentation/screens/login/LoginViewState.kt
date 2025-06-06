package com.insearching.notemark.presentation.screens.login

data class LoginViewState(
    val email: String,
    val password: String,
    val loginEnabled: Boolean,
    val isLoading: Boolean
) {
    companion object {
        val Initial = LoginViewState(
            email = "",
            password = "",
            loginEnabled = false,
            isLoading = false
        )
    }
}