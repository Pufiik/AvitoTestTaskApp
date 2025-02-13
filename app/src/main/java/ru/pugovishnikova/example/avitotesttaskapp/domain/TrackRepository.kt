package ru.pugovishnikova.example.avitotesttaskapp.domain
import ru.pugovishnikova.example.avitotesttaskapp.domain.model.Track
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.NetworkError
import ru.pugovishnikova.example.avitotesttaskapp.util.Result

interface TrackRepository {
    suspend fun getAllTracks(): Result<List<Track>, NetworkError>
}