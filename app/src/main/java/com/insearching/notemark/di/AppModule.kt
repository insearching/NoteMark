package com.insearching.notemark.di

import com.insearching.notemark.MainViewModel
import com.insearching.notemark.data.session.DataStoreSessionStorage
import com.insearching.notemark.domain.SessionStorage
import com.insearching.notemark.domain.usecase.ValidateEmailUseCase
import com.insearching.notemark.domain.usecase.ValidatePasswordUseCase
import com.insearching.notemark.domain.usecase.ValidateRepeatPasswordUseCase
import com.insearching.notemark.domain.usecase.ValidateUsernameUseCase
import com.insearching.notemark.presentation.screens.login.LoginViewModel
import com.insearching.notemark.presentation.screens.note_details.NoteDetailsViewModel
import com.insearching.notemark.presentation.screens.note_list.NoteListViewModel
import com.insearching.notemark.presentation.screens.register.RegisterViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::NoteListViewModel)
    viewModelOf(::NoteDetailsViewModel)

    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidateUsernameUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidateRepeatPasswordUseCase)

    singleOf(::DataStoreSessionStorage) { bind<SessionStorage>() }
}