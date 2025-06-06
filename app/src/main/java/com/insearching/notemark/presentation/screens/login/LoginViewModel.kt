package com.insearching.notemark.presentation.screens.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow<LoginViewState>(LoginViewState.Initial)
    val state = _state.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChanged -> {
                _state.update { it.copy(email = action.email) }
                verifyForm()
            }

            is LoginAction.OnPassChanged -> {
                _state.update { it.copy(password = action.pass) }
                verifyForm()
            }

            LoginAction.OnLogin -> {
                _events.trySend(LoginEvent.OnLogin)
            }

            LoginAction.OnCreateNewAccount -> {
                _events.trySend(LoginEvent.OnCreateNewAccount)
            }
        }
    }

    fun verifyForm() {
        val hasError = listOf(
            state.value.email,
            state.value.password
        ).any { it.isBlank() }
        _state.update { it.copy(loginEnabled = !hasError) }
    }
}