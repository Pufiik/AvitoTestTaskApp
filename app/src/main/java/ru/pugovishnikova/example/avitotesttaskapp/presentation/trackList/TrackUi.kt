package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList
import ru.pugovishnikova.example.avitotesttaskapp.domain.model.Track

data class TrackUi (
    val imageId: String,
    val title: String,
    val authorName: String
)

fun Track.toTrackUi(): TrackUi = TrackUi(
    title = title,
    authorName = artist?.name?:"Unknown",
    imageId = album?.coverXl?:"1"
)