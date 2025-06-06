package com.insearching.notemark.presentation.screens.register

sealed interface RegisterAction {
    data class OnUserNameChanged(val userName: String) : RegisterAction
    data class OnEmailChanged(val email: String) : RegisterAction
    data class OnPassChanged(val password: String) : RegisterAction
    data class OnPassRepeatChanged(val repeatPassword: String) : RegisterAction
    data object OnCreateAccount : RegisterAction
    data object OnLogin : RegisterAction
}