package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

sealed interface TrackListAction {
    data class OnTrackClick(val track: TrackUi, val navigate: () -> Unit) : TrackListAction
    data class OnSearchButtonClick(val query: String) : TrackListAction
    data object OnReloadButtonClick : TrackListAction
    data class OnBackClick(val navigate: () -> Unit) : TrackListAction
    data object OnPrevClick : TrackListAction
    data object OnNextClick : TrackListAction
    data object OnPlayPause : TrackListAction
    data class OnSeekTo(val position: Int): TrackListAction
    data object OnSeekToStart: TrackListAction
    data class OnTrackLoaded(val duration: Int) : TrackListAction
    data object OnDownloadClick: TrackListAction
    data object OnDownloadScreenClick: TrackListAction
}