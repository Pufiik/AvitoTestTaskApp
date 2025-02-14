package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTrack
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Track
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.TrackInfo
import ru.pugovishnikova.example.avitotesttaskapp.util.Utils


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
    authorName = artist?.name?:Utils.getAuthorName(),
    imageId = album?.coverXl?:Utils.getImageId(),
    duration = duration?:0,
    albumTitle = album?.title,
    artistName = artist?.name?:Utils.getAuthorName(),
)

fun SearchTrack.toTrackUi(): TrackUi = TrackUi(
    id = id,
    title = title,
    authorName = artist?.name?:Utils.getAuthorName(),
    imageId = album?.coverXl?:Utils.getImageId(),
    duration = duration?:0,
    albumTitle = album?.title,
    artistName = artist?.name?:Utils.getAuthorName(),
)

fun TrackInfo.toTrackUi(): TrackUi = TrackUi(
    id = id,
    title = title,
    authorName = artist?.name?:Utils.getAuthorName(),
    imageId = album?.coverXl?: Utils.getImageId(),
    duration = duration?:0,
    albumTitle = album?.title,
    artistName = artist?.name?:Utils.getAuthorName(),
)
