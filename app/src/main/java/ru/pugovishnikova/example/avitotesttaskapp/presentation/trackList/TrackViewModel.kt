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
    private var currentTrackIndex: Int = 0
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
                _state.update {
                    it.copy(
                        selectedTrack = action.track
                    )
                }
                getTrackById(action.track.id)
                action.navigate()
            }

            is TrackListAction.OnSearchButtonClick -> {
                val query = action.query
                searchTracksByName(query)
            }

            is TrackListAction.OnReloadButtonClick -> {
                getAllTracks()
            }

            is TrackListAction.OnBackClick -> {
                action.navigate()
            }

            is TrackListAction.OnNextClick -> {
                playNextTrack()
            }

            is TrackListAction.OnPrevClick -> {
                playPreviousTrack()
            }
        }
    }

    private val _events = Channel<TrackListEvent>()
    val events = _events.receiveAsFlow()

    private fun getAllTracks() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }
            withContext(Dispatchers.IO) {
                trackUseCases.getAllTracksUseCase.invoke()
                    .onSuccess { tracks ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                tracks = tracks.map { track -> track.toTrackUi() }
                            )
                        }
                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                        _events.send(TrackListEvent.Error(error))

                    }
            }
        }
    }

    private fun getTrackById(id: Long) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }
            withContext(Dispatchers.IO) {
                trackUseCases.getTrackByIdUseCase.invoke(id)
                    .onSuccess { track ->
                        val trackU = track.toTrackUi()
                        val flag = (trackU.imageId == null)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                selectedTrack = if (flag) state.value.selectedTrack else track.toTrackUi()
                            )
                        }
                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = true
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
                    isLoading = true,
                    isError = false
                )
            }
            withContext(Dispatchers.IO) {
                val tracksWithName =
                    async { trackUseCases.searchTracksUseCases.invoke("track:\"$query\"") }
                val tracksWithArtist =
                    async { trackUseCases.searchTracksUseCases.invoke("artist:\"$query\"") }
                listOf(tracksWithName, tracksWithArtist)
                    .awaitAll()
                    .combineResults()
                    .onSuccess { tracks ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = false,
                                tracks = tracks.map { track -> track.toTrackUi() }
                            )
                        }
                    }
                    .onError { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }
                        _events.send(TrackListEvent.Error(error))
                    }
            }
        }
    }

    fun playNextTrack() {
        if (currentTrackIndex < state.value.tracks.size - 1) {
            currentTrackIndex++
            updateTrack()
        }
    }

    fun playPreviousTrack() {
        if (currentTrackIndex > 0) {
            currentTrackIndex--
            updateTrack()
        }
    }

    private fun updateTrack() {
        val newTrack = state.value.tracks[currentTrackIndex]
        _state.update {
            it.copy(
                selectedTrack = newTrack
            )
        }
    }
}