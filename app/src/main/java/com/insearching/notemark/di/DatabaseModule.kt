package com.insearching.notemark.di


import com.insearching.notemark.data.local.AppDatabase
import com.insearching.notemark.data.local.DatabaseFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseFactory(androidContext()).create().build() }
    single { get<AppDatabase>().noteDao() }
}
