//package ru.pugovishnikova.example.avitotesttaskapp.domain.usecases
//
//import DownloadedTrackEntity
//import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackRepository
//
//
//class UpdateTracksUseCase(
//    private val repository: TrackRepository,
//) : suspend () -> List<DownloadedTrackEntity> {
//    override suspend fun invoke(): List<DownloadedTrackEntity> {
//        return repository.getDownloadedTracks()
//    }
//}