package ru.pugovishnikova.example.avitotesttaskapp.domain.usecases

import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
import ru.pugovishnikova.example.avitotesttaskapp.domain.model.Track
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.NetworkError
import ru.pugovishnikova.example.avitotesttaskapp.util.Result

class GetAllTracksUseCase (
    private val repository: TrackRepository
) : suspend () -> Result<List<Track>, NetworkError> {
    override suspend fun invoke(): Result<List<Track>, NetworkError> {
        return repository.getAllTracks()
    }
}
