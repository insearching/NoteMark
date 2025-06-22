package com.insearching.notemark.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

class AppCoroutineScope : CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.Default)

val coroutineModule = module {
    single<CoroutineScope> { AppCoroutineScope() }
}