package com.insearching.notemark.presentation.screens.login

sealed interface LoginEvent {
    data object OnCreateNewAccount : LoginEvent
    data object OnLogin : LoginEvent
}