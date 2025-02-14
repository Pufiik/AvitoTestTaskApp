package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import androidx.compose.runtime.Immutable

@Immutable
data class TrackListState(
    val isLoading: Boolean = false,
    val tracks: List<TrackUi> = emptyList(),
    val selectedTrack: TrackUi? = null,
    val isError: Boolean = false
)
