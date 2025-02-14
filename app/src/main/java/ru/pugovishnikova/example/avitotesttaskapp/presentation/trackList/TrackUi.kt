package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTrack
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Track
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.TrackInfo


data class TrackUi (
    val id: Long,
    val imageId: String,
    val title: String,
    val authorName: String,
    val duration: Int,
    val albumTitle: String?,
    val artistName: String
)

fun Track.toTrackUi(): TrackUi = TrackUi(
    id = id,
    title = title,
    authorName = artist?.name?:"Unknown",
    imageId = album?.coverXl?:"1",
    duration = duration?:0,
    albumTitle = album?.title,
    artistName = artist?.name?:"Unknown"
)

fun SearchTrack.toTrackUi(): TrackUi = TrackUi(
    id = id,
    title = title,
    authorName = artist?.name?:"Unknown",
    imageId = album?.coverXl?:"1",
    duration = duration?:0,
    albumTitle = album?.title,
    artistName = artist?.name?:"Unknown"
)

fun TrackInfo.toTrackUi(): TrackUi = TrackUi(
    id = id,
    title = title,
    authorName = artist?.name?:"Unknown",
    imageId = album?.coverXl?:"1",
    duration = duration?:0,
    albumTitle = album?.title,
    artistName = artist?.name?:"Unknown"
)