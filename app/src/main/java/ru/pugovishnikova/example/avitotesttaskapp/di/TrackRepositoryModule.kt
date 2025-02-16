package ru.pugovishnikova.example.avitotesttaskapp.di

import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackService.TrackService
import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.room.Room
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.pugovishnikova.example.avitotesttaskapp.BuildConfig
//import ru.pugovishnikova.example.avitotesttaskapp.data.AppDatabase
import ru.pugovishnikova.example.avitotesttaskapp.data.remote.TrackApiService
import ru.pugovishnikova.example.avitotesttaskapp.data.repository.TrackRepositoryImpl
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetAllTracksUseCase
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackUseCases
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.SearchTracksUseCase
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetTrackByIdUseCase
//import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.DownloadTrackUseCase
//import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.UpdateTracksUseCase
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackService.TrackNotificationManager
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.DownloadViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.SharedPlayerViewModel
import java.util.concurrent.TimeUnit

private fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}

fun provideTrackService(context: Context): TrackService {
    return TrackService()
}


fun provideExoPlayer(context: Context): ExoPlayer {
    return ExoPlayer.Builder(context).build().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                .setUsage(C.USAGE_MEDIA)
                .build(),
            true
        )
        playWhenReady = true
    }
}

private fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()


private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

private fun provideService(retrofit: Retrofit): TrackApiService =
    retrofit.create(TrackApiService::class.java)

//val databaseModule = module {
//
//    single {
//        provideDatabase(get())
//    }
//
//    single {
//        get<AppDatabase>().downloadedTrackDao()
//    }
//}
//
//fun provideDatabase(context: Context): AppDatabase {
//    return Room.databaseBuilder(
//        context.applicationContext,
//        AppDatabase::class.java,
//        "downloaded_tracks_db"
//    ).build()
//}

val networkModule = module {
    singleOf(::provideHttpClient)
    singleOf(::provideConverterFactory)
    singleOf(::provideRetrofit)
    singleOf(::provideService)
}

fun provideMediaSession(context: Context, player: ExoPlayer): MediaSession {
    return MediaSession.Builder(context, player).build()
}

val serviceModule = module {
    single { provideMediaSession(get(), get()) } // Создаем и предоставляем MediaSession
    single { TrackNotificationManager(get(), get()) }
    single { TrackService() } // Сервис
}


val exoPlayerModule = module {
    singleOf(::provideExoPlayer)
}

val repositoryModule = module {
    singleOf(::TrackRepositoryImpl).bind(TrackRepository::class)
}

val viewModelModule = module {
    viewModelOf(::SharedPlayerViewModel)
    viewModelOf(::DownloadViewModel)
    viewModelOf(::TrackViewModel)
}


val useCasesModule = module {
//    singleOf(::UpdateTracksUseCase)
//    singleOf(::DownloadTrackUseCase)
    singleOf(::GetAllTracksUseCase)
    singleOf(::SearchTracksUseCase)
    singleOf(::GetTrackByIdUseCase)
    singleOf(::TrackUseCases)
}