package ru.pugovishnikova.example.avitotesttaskapp.data.remote

import TracksDTOResponse
import retrofit2.Response
import retrofit2.http.GET

interface TrackApiService {
    @GET("chart/tracks")
    suspend fun getAllTracks(): Response<TracksDTOResponse>
}