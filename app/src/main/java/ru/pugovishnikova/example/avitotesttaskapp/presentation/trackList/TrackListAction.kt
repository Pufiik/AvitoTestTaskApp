package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

sealed interface TrackListAction {
    data class OnTrackClick(val track: TrackUi, val navigate: () -> Unit) : TrackListAction
    data class OnSearchButtonClick(val query: String) : TrackListAction
    data object OnReloadButtonClick : TrackListAction
    data class OnBackClick(val navigate: () -> Unit) : TrackListAction
}