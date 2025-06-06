package com.insearching.notemark.presentation.screens.register

import androidx.lifecycle.ViewModel
import com.insearching.notemark.domain.usecase.ValidateEmailUseCase
import com.insearching.notemark.domain.usecase.ValidatePasswordUseCase
import com.insearching.notemark.domain.usecase.ValidateRepeatPasswordUseCase
import com.insearching.notemark.domain.usecase.ValidateUsernameUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel(
    private val validateEmail: ValidateEmailUseCase,
    private val validateUsername: ValidateUsernameUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatPassword: ValidateRepeatPasswordUseCase,
) : ViewModel() {

    private val _events = Channel<RegisterEvent>()
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow<RegisterViewState>(RegisterViewState.Initial)
    val state = _state.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnCreateAccount -> {

            }

            RegisterAction.OnLogin -> {
                _events.trySend(RegisterEvent.OnLoginEvent)
            }

            is RegisterAction.OnUserNameChanged -> {
                _state.update { it.copy(username = it.username.copy(value = action.userName)) }
                verifyAllFields()
            }

            is RegisterAction.OnEmailChanged -> {
                _state.update { it.copy(email = it.email.copy(value = action.email)) }
                verifyAllFields()
            }

            is RegisterAction.OnPassChanged -> {
                _state.update { it.copy(password = it.password.copy(value = action.password)) }
                verifyAllFields()
            }

            is RegisterAction.OnPassRepeatChanged -> {
                _state.update { it.copy(repeatPassword = it.repeatPassword.copy(value = action.repeatPassword)) }
                verifyAllFields()
            }
        }
    }

    private fun verifyAllFields() {
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
                loginEnabled = !hasError
            )
        }
    }
}