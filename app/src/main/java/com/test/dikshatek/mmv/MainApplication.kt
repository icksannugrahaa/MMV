package com.test.dikshatek.mmv

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.test.dikshatek.mmv.di.AppModule.networkModule
import com.test.dikshatek.mmv.di.AppModule.repositoryModule
import com.test.dikshatek.mmv.di.AppModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application()  {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}