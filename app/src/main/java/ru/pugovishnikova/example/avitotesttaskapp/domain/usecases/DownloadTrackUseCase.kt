//package ru.pugovishnikova.example.avitotesttaskapp.domain.usecases
//
//import DownloadedTrackEntity
//import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
//
//class DownloadTrackUseCase(
//    private val repository: TrackRepository
//) : suspend (DownloadedTrackEntity) -> Unit {
//    override suspend fun invoke(track: DownloadedTrackEntity) {
//        return repository.downloadTrack(track)
//    }
//}