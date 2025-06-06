package com.insearching.notemark.presentation.screens.login

sealed interface LoginAction {
    data class OnEmailChanged(val email: String) : LoginAction
    data class OnPassChanged(val pass: String) : LoginAction
    data object OnLogin : LoginAction
    data object OnCreateNewAccount : LoginAction
}