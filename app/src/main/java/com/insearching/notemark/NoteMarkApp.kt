package com.insearching.notemark

import android.app.Application
import com.insearching.notemark.di.appModule
import com.insearching.notemark.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber


class NoteMarkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NoteMarkApp)
            androidLogger()

            modules(appModule, remoteModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}