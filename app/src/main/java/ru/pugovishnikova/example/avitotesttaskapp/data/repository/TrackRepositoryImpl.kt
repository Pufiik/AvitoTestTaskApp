package ru.pugovishnikova.example.avitotesttaskapp.data.repository

import TracksDTOResponse
import ru.pugovishnikova.example.avitotesttaskapp.data.remote.TrackApiService
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTrack
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTracksDTOResponse
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Track
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.TrackInfo
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.TrackResponse
import ru.pugovishnikova.example.avitotesttaskapp.domain.toSearchTrack
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.NetworkError
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.responseToResult
import ru.pugovishnikova.example.avitotesttaskapp.util.Result
import ru.pugovishnikova.example.avitotesttaskapp.util.map
import ru.pugovishnikova.example.avitotesttaskapp.domain.toTrack
import ru.pugovishnikova.example.avitotesttaskapp.domain.toTrackInfo


class TrackRepositoryImpl(
    private val trackService: TrackApiService
) : TrackRepository {
    override suspend fun getAllTracks(): Result<List<Track>, NetworkError> {
        try {
            val response = trackService.getAllTracks()
            return responseToResult<TracksDTOResponse>(response).map { res ->
                res.tracks.data.map { it.toTrack() }
            }
        } catch (e: Exception) {
            val response = null
            return responseToResult<TracksDTOResponse>(response).map { res ->
                res.tracks.data.map { it.toTrack() }
            }
        }
    }

    override suspend fun searchTracks(query: String): Result<List<SearchTrack>, NetworkError> {
        try {
            val response = trackService.searchTracks(query)
            return responseToResult<SearchTracksDTOResponse>(response).map { res ->
                res.data.map { it.toSearchTrack() }
            }
        } catch (e: Exception) {
            val response = null
            return responseToResult<SearchTracksDTOResponse>(response).map { res ->
                res.data.map { it.toSearchTrack() }
            }
        }
    }

    override suspend fun getTrackById(id: Long): Result<TrackInfo, NetworkError> {
        try {
            val response = trackService.getTrackById(id)
            return responseToResult<TrackResponse>(response).map { it.toTrackInfo() }
        } catch (e: Exception) {
            val response = null
            return responseToResult<TrackInfo>(response)
        }
    }
}