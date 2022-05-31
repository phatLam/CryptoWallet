package com.example.cryptowallet

import android.app.Application
import com.example.cryptowallet.di.appModule
import com.example.data.remote.di.apiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DemoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@DemoApplication)
            modules(appModule, apiModule)
        }
    }
}