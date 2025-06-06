package com.insearching.notemark.presentation.screens.register

sealed interface RegisterEvent {
    data object OnLoginEvent : RegisterEvent
}