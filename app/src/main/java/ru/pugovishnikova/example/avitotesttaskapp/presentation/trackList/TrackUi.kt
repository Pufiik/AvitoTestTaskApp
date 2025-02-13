package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Track


data class TrackUi (
    val imageId: String,
    val title: String,
    val authorName: String,
    val duration: Int,
    val albumTitle: String?,
    val artistName: String
)

fun Track.toTrackUi(): TrackUi = TrackUi(
    title = title,
    authorName = artist?.name?:"Unknown",
    imageId = album?.coverXl?:"1",
    duration = duration?:0,
    albumTitle = album?.title,
    artistName = artist?.name?:"Unknown"
)