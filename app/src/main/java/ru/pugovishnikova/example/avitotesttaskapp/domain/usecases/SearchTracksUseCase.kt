package ru.pugovishnikova.example.avitotesttaskapp.domain.usecases

import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTrack
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.NetworkError
import ru.pugovishnikova.example.avitotesttaskapp.util.Result

class SearchTracksUseCase(
    private val repository: TrackRepository,
) : suspend (String) -> Result<List<SearchTrack>, NetworkError> {
    override suspend fun invoke(query: String): Result<List<SearchTrack>, NetworkError> {
        return repository.searchTracks(query)
    }
}