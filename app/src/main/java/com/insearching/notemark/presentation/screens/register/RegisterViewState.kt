package com.insearching.notemark.presentation.screens.register

import com.insearching.notemark.core.FieldInput

data class RegisterViewState(
    val email: FieldInput,
    val username: FieldInput,
    val password: FieldInput,
    val repeatPassword: FieldInput,
    val registerEnabled: Boolean,
    val isLoading: Boolean,
) {
    companion object {
        val Initial = RegisterViewState(
            email = FieldInput("", null),
            username = FieldInput("", null),
            password = FieldInput("", null),
            repeatPassword = FieldInput("", null),
            registerEnabled = false,
            isLoading = false,
        )
    }
}