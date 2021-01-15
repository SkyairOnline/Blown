package com.arudo.blown

import android.app.Application
import com.arudo.blown.core.di.databaseModule
import com.arudo.blown.core.di.networkModule
import com.arudo.blown.core.di.repositoryModule
import com.arudo.blown.di.useCaseModule
import com.arudo.blown.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Blown : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@Blown)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}