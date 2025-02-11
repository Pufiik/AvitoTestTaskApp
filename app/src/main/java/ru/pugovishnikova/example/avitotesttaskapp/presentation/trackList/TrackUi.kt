package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import androidx.annotation.DrawableRes
import ru.pugovishnikova.example.avitotesttaskapp.R
import ru.pugovishnikova.example.avitotesttaskapp.domain.Track

data class TrackUi (
    @DrawableRes val imageId: Int,
    val title: String,
    val authorName: String
)

fun Track.toTrackUi(): TrackUi = TrackUi(
    title = title,
    authorName = authorName,
    imageId = R.drawable.stub_icon
)