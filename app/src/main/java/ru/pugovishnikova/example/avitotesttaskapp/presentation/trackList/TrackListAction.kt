package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

sealed interface TrackListAction {
    data class OnTrackClick(val track: TrackUi): TrackListAction
    data class OnSearchButtonClick(val query: String): TrackListAction
}