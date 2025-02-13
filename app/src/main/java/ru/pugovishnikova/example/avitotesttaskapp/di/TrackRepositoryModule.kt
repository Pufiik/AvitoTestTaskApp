package ru.pugovishnikova.example.avitotesttaskapp.di

import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.pugovishnikova.example.avitotesttaskapp.data.remote.TrackApiService
import ru.pugovishnikova.example.avitotesttaskapp.data.repository.TrackRepositoryImpl
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetAllTracksUseCase
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackUseCases
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.SearchTracksUseCase
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackViewModel
import java.util.concurrent.TimeUnit

//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import jakarta.inject.Singleton
//import ru.pugovishnikova.example.avitotesttaskapp.data.repository.TrackRepositoryImpl
//import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
//
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class TrackRepositoryModule {
//
//    @Binds
//    @Singleton
//    abstract fun bindTrackRepository(
//        trackRepositoryImpl: TrackRepositoryImpl
//    ): TrackRepository
//}
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
        .baseUrl("https://api.deezer.com/")
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

fun provideService(retrofit: Retrofit): TrackApiService =
    retrofit.create(TrackApiService::class.java)


val networkModule = module {
//    single { provideHttpClient() }
//    single { provideConverterFactory() }
//    single { provideRetrofit(get(),get()) }
//    single { provideService(get()) }

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
    singleOf(::TrackUseCases)
}