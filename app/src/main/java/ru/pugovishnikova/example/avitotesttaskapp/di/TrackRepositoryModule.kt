package ru.pugovishnikova.example.avitotesttaskapp.di

import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.pugovishnikova.example.avitotesttaskapp.BuildConfig
import ru.pugovishnikova.example.avitotesttaskapp.data.remote.TrackApiService
import ru.pugovishnikova.example.avitotesttaskapp.data.repository.TrackRepositoryImpl
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetAllTracksUseCase
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackUseCases
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.SearchTracksUseCase
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetTrackByIdUseCase
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackViewModel
import java.util.concurrent.TimeUnit

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}


fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()


fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

fun provideService(retrofit: Retrofit): TrackApiService =
    retrofit.create(TrackApiService::class.java)


val networkModule = module {
    singleOf(::provideHttpClient)
    singleOf(::provideConverterFactory)
    singleOf(::provideRetrofit)
    singleOf(::provideService)
}

val repositoryModule = module {
    singleOf(::TrackRepositoryImpl).bind(TrackRepository::class)
}

val viewModelModule = module {
    viewModelOf(::TrackViewModel)
}

val useCasesModule = module {
    singleOf(::GetAllTracksUseCase)
    singleOf(::SearchTracksUseCase)
    singleOf(::GetTrackByIdUseCase)
    singleOf(::TrackUseCases)
}