package ru.pugovishnikova.example.avitotesttaskapp.domain

//import DownloadedTrackEntity
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTrack
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Track
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.TrackInfo
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.NetworkError
import ru.pugovishnikova.example.avitotesttaskapp.util.Result

interface TrackRepository {
    suspend fun getAllTracks(): Result<List<Track>, NetworkError>
    suspend fun searchTracks(query: String): Result<List<SearchTrack>, NetworkError>
    suspend fun getTrackById(id: Long): Result<TrackInfo, NetworkError>
//    suspend fun getDownloadedTracks(): List<DownloadedTrackEntity>
//    suspend fun downloadTrack(track: DownloadedTrackEntity)
}