//package ru.pugovishnikova.example.avitotesttaskapp.di
//
//import android.content.Context
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import jakarta.inject.Singleton
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import ru.pugovishnikova.example.avitotesttaskapp.BuildConfig
//import ru.pugovishnikova.example.avitotesttaskapp.data.remote.TrackApiService
//import ru.pugovishnikova.example.avitotesttaskapp.data.repository.TrackRepositoryImpl
//import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
//import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackUseCases
//import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetAllTracksUseCase
//
//@Module
//@InstallIn(SingletonComponent::class)
//class TrackModule {
//
////    @Provides
////    @Singleton
////    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
////        return HttpLoggingInterceptor().apply {
////            level = HttpLoggingInterceptor.Level.BODY
////        }
////    }
//
////    @Provides
////    @Singleton
////    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
////        return OkHttpClient.Builder()
////            .addInterceptor(loggingInterceptor)
////            .build()
////    }
//
////    @Provides
////    @Singleton
////    fun provideContext(@ApplicationContext context: Context): Context {
////        return context
////    }
//
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("https://api.deezer.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
////    @Provides
////    fun provideTrackRepository(retrofit: Retrofit): TrackRepository {
////        return retrofit.create(TrackRepository::class.java)
////    }
//
//    @Provides
//    @Singleton
//    fun provideApiService(retrofit: Retrofit): TrackApiService {
//        return retrofit.create(TrackApiService::class.java)
//    }
//
////    @Provides
////    fun provideGetAllTracksUseCase(repository: TrackRepository): GetAllTracksUseCase {
////        return GetAllTracksUseCase(repository)
////    }
//
////    @Provides
////    @Singleton
////    fun provideTrackRepository(apiService: TrackApiService): TrackRepository {
////        return TrackRepositoryImpl(apiService)
////    }
//
//    @Provides
//    @Singleton
//    fun provideTrackUseCases(
//        getAllTracksUseCase: GetAllTracksUseCase
//    ): TrackUseCases {
//        return TrackUseCases(getAllTracksUseCase)
//    }
//
//}
//
