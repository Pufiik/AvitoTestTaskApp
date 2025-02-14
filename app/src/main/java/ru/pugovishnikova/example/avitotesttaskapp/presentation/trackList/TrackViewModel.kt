package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackUseCases
import ru.pugovishnikova.example.avitotesttaskapp.util.combineResults
import ru.pugovishnikova.example.avitotesttaskapp.util.onError
import ru.pugovishnikova.example.avitotesttaskapp.util.onSuccess


class TrackViewModel(
    private val trackUseCases: TrackUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(TrackListState())
    val state = _state
        .onStart { getAllTracks() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            TrackListState()
        )

    fun onAction(action: TrackListAction) {
        when (action) {
            is TrackListAction.OnTrackClick -> {
                action.navigate()
                _state.update {
                    it.copy(
                        selectedTrack = action.track
                    )
                }
            }

            is TrackListAction.OnSearchButtonClick -> {
                val query = action.query
                searchTracksByName(query)
            }
        }
    }

    private val _events = Channel<TrackListEvent>()
    val events = _events.receiveAsFlow()

    private fun getAllTracks() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            withContext(Dispatchers.IO) {
                trackUseCases.getAllTracksUseCase.invoke()
                    .onSuccess { tracks ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                tracks = tracks.map { track -> track.toTrackUi() }
                            )
                        }
                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _events.send(TrackListEvent.Error(error))
                    }
            }
        }
    }

    private fun searchTracksByName(query: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            withContext(Dispatchers.IO) {
                val tracksWithName = async { trackUseCases.searchTracksUseCases.invoke("track:\"$query\"") }
                val tracksWithArtist = async { trackUseCases.searchTracksUseCases.invoke("artist:\"$query\"") }
                listOf(tracksWithName, tracksWithArtist)
                    .awaitAll()
                    .combineResults()
                    .onSuccess { tracks ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            tracks = tracks.map { track -> track.toTrackUi() }
                        )
                    }
                }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _events.send(TrackListEvent.Error(error))
                    }
            }
        }
    }
}