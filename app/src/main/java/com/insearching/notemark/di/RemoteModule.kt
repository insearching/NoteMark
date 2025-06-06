package com.insearching.notemark.di


import com.insearching.notemark.data.remote.HttpClientFactory
import com.insearching.notemark.data.remote.RemoteAuthDataSource
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val remoteModule = module {
    single { HttpClientFactory.create(CIO.create(), sessionStorage = get()) }
    singleOf(::RemoteAuthDataSource)
}