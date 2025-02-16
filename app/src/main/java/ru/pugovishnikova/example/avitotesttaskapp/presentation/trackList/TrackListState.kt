package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class TrackListState(
    val isLoading: Boolean = false,
    val tracks: List<TrackUi> = emptyList(),
    val downloadTracks: List<TrackUi> = emptyList(),
    val isDownloaded: Boolean = false,
    val isPlaying: Boolean = false,
    val selectedTrack: TrackUi? = null,
    val isError: Boolean = false,
    val currentPosition: Int = 0,
    var isServicePlaying: Boolean = false,
    val duration: Int = 0
)
