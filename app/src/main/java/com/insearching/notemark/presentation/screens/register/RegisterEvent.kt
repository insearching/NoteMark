package com.insearching.notemark.presentation.screens.register

sealed interface RegisterEvent {
    data object OnLoginEvent : RegisterEvent
    data object OnRegisterSuccess : RegisterEvent
    data class OnRegisterFailed(val message: String) : RegisterEvent
}