package ru.pugovishnikova.example.avitotesttaskapp.data.remote

import TracksDTOResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTracksDTOResponse

interface TrackApiService {
    @GET("chart/tracks")
    suspend fun getAllTracks(): Response<TracksDTOResponse>

    @GET("search")
    suspend fun searchTracks(@Query("q") query: String): Response<SearchTracksDTOResponse>
}