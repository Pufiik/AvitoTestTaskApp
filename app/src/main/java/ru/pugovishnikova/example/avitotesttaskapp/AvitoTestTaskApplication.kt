package ru.pugovishnikova.example.avitotesttaskapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.pugovishnikova.example.avitotesttaskapp.di.exoPlayerModule
import ru.pugovishnikova.example.avitotesttaskapp.di.networkModule
import ru.pugovishnikova.example.avitotesttaskapp.di.repositoryModule
import ru.pugovishnikova.example.avitotesttaskapp.di.useCasesModule
import ru.pugovishnikova.example.avitotesttaskapp.di.viewModelModule


class AvitoTestTaskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AvitoTestTaskApplication)
            androidLogger()

            modules(networkModule, repositoryModule, viewModelModule, useCasesModule, exoPlayerModule)
        }

    }
}