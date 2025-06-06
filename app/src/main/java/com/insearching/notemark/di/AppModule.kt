package com.insearching.notemark.di

import com.insearching.notemark.MainViewModel
import com.insearching.notemark.domain.usecase.ValidateEmailUseCase
import com.insearching.notemark.domain.usecase.ValidatePasswordUseCase
import com.insearching.notemark.domain.usecase.ValidateRepeatPasswordUseCase
import com.insearching.notemark.domain.usecase.ValidateUsernameUseCase
import com.insearching.notemark.presentation.screens.login.LoginViewModel
import com.insearching.notemark.presentation.screens.register.RegisterViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)

    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidateUsernameUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidateRepeatPasswordUseCase)
}