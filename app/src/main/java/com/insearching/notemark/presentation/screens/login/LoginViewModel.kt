package com.insearching.notemark.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insearching.notemark.data.remote.Response
import com.insearching.notemark.domain.AuthRepository
import com.insearching.notemark.domain.usecase.ValidateEmailUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

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
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    val response = authRepository.login(state.value.email, state.value.password)
                    _state.update { it.copy(isLoading = false) }
                    if (response is Response.Success) {
                        _events.trySend(LoginEvent.OnLoginSuccess)
                    } else {
                        _events.trySend(LoginEvent.OnLoginFailed)
                    }
                }
            }

            LoginAction.OnCreateNewAccount -> {
                _events.trySend(LoginEvent.OnCreateNewAccount)
            }
        }
    }

    fun verifyForm() {
        val emailValid = validateEmailUseCase(state.value.email).successful
        val passValid = state.value.password.isNotBlank()

        _state.update { it.copy(loginEnabled = emailValid && passValid) }
    }
}