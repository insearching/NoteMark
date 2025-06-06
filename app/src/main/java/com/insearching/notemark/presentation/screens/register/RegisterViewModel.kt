package com.insearching.notemark.presentation.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insearching.notemark.data.remote.Response
import com.insearching.notemark.domain.AuthRepository
import com.insearching.notemark.domain.usecase.ValidateEmailUseCase
import com.insearching.notemark.domain.usecase.ValidatePasswordUseCase
import com.insearching.notemark.domain.usecase.ValidateRepeatPasswordUseCase
import com.insearching.notemark.domain.usecase.ValidateUsernameUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val validateEmail: ValidateEmailUseCase,
    private val validateUsername: ValidateUsernameUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatPassword: ValidateRepeatPasswordUseCase,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _events = Channel<RegisterEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow<RegisterViewState>(RegisterViewState.Initial)
    val state = _state.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnLogin -> {
                _events.trySend(RegisterEvent.OnLoginEvent)
            }

            is RegisterAction.OnUserNameChanged -> {
                _state.update { it.copy(username = it.username.copy(value = action.userName)) }
                verifyForm()
            }

            is RegisterAction.OnEmailChanged -> {
                _state.update { it.copy(email = it.email.copy(value = action.email)) }
                verifyForm()
            }

            is RegisterAction.OnPassChanged -> {
                _state.update { it.copy(password = it.password.copy(value = action.password)) }
                verifyForm()
            }

            is RegisterAction.OnPassRepeatChanged -> {
                _state.update { it.copy(repeatPassword = it.repeatPassword.copy(value = action.repeatPassword)) }
                verifyForm()
            }

            RegisterAction.OnCreateAccount -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    val response = authRepository.register(
                        _state.value.username.value,
                        _state.value.email.value,
                        _state.value.password.value
                    )
                    _state.update { it.copy(isLoading = false) }
                    if (response is Response.Success) {
                        _events.trySend(RegisterEvent.OnRegisterSuccess)
                    } else if (response is Response.Error) {
                        _events.trySend(
                            RegisterEvent.OnRegisterFailed(
                                response.error.message ?: "Failed to create account"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun verifyForm() {
        val emailResult = validateEmail(_state.value.email.value)
        val usernameResult = validateUsername(_state.value.username.value)
        val passwordResult = validatePassword(_state.value.password.value)
        val repeatPasswordResult =
            validateRepeatPassword(_state.value.password.value, _state.value.repeatPassword.value)
        val hasError = listOf(
            emailResult,
            usernameResult,
            passwordResult,
            repeatPasswordResult
        ).any { !it.successful }
        _state.update {
            it.copy(
                email = it.email.copy(error = emailResult.errorMessage),
                username = it.username.copy(error = usernameResult.errorMessage),
                password = it.password.copy(error = passwordResult.errorMessage),
                repeatPassword = it.repeatPassword.copy(error = repeatPasswordResult.errorMessage),
                registerEnabled = !hasError
            )
        }
    }
}