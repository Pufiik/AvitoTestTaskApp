package ru.pugovishnikova.example.avitotesttaskapp.domain.usecases

import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Track
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.TrackInfo
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.NetworkError
import ru.pugovishnikova.example.avitotesttaskapp.util.Result

class GetTrackByIdUseCase(
    private val repository: TrackRepository
) : suspend (Long) -> Result<TrackInfo, NetworkError> {
    override suspend fun invoke(id: Long): Result<TrackInfo, NetworkError> {
        return repository.getTrackById(id)
    }
}