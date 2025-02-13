package ru.pugovishnikova.example.avitotesttaskapp.data.repository
import TracksDTOResponse
import ru.pugovishnikova.example.avitotesttaskapp.data.remote.TrackApiService
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
import ru.pugovishnikova.example.avitotesttaskapp.domain.model.Track
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.NetworkError
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.responseToResult
import ru.pugovishnikova.example.avitotesttaskapp.util.Result
import ru.pugovishnikova.example.avitotesttaskapp.util.map
import ru.pugovishnikova.example.avitotesttaskapp.domain.toTrack



class TrackRepositoryImpl (
    private val trackService: TrackApiService
) : TrackRepository {
    override suspend fun getAllTracks(): Result<List<Track>, NetworkError> {
        val response = trackService.getAllTracks()
        return responseToResult<TracksDTOResponse>(response).map { res ->
            res.tracks.data.map { it.toTrack() }
        }
    }
}