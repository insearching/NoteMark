package com.insearching.notemark.di

import com.insearching.notemark.data.repository.AuthRepositoryImpl
import com.insearching.notemark.data.repository.NoteRepositoryImpl
import com.insearching.notemark.domain.AuthRepository
import com.insearching.notemark.domain.NoteRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoriesModule = module {
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::NoteRepositoryImpl) { bind<NoteRepository>() }
}