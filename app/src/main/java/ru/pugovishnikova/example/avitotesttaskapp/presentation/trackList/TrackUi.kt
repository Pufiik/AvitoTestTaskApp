package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList
//import DownloadedTrackEntity
import kotlinx.serialization.Serializable
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTrack
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Track
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.TrackInfo
import ru.pugovishnikova.example.avitotesttaskapp.util.Utils

@Serializable
data class TrackUi (
    val id: Long,
    val imageId: String?,
    val title: String,
    val authorName: String,
    val duration: Int,
    val albumTitle: String?,
    val artistName: String,
    val preview: String,
    val pos: Int = -1
)

fun Track.toTrackUi(): TrackUi = TrackUi(
    id = id,
    title = title,
    authorName = artist?.name?:Utils.getAuthorName(),
    imageId = album?.coverXl?:Utils.getImageId(),
    duration = duration?:0,
    albumTitle = album?.title,
    artistName = artist?.name?:Utils.getAuthorName(),
    preview = preview?:Utils.getEmptyString()
)

fun SearchTrack.toTrackUi(): TrackUi = TrackUi(
    id = id,
    title = title,
    authorName = artist?.name?:Utils.getAuthorName(),
    imageId = album?.coverXl?:Utils.getImageId(),
    duration = duration?:0,
    albumTitle = album?.title,
    artistName = artist?.name?:Utils.getAuthorName(),
    preview = preview?:Utils.getEmptyString()
)

fun TrackInfo.toTrackUi(): TrackUi = TrackUi(
    id = id,
    title = title,
    authorName = artist?.name?:Utils.getAuthorName(),
    imageId = album?.coverXl,
    duration = duration?:0,
    albumTitle = album?.title,
    artistName = artist?.name?:Utils.getAuthorName(),
    preview = preview?:Utils.getEmptyString()
)
