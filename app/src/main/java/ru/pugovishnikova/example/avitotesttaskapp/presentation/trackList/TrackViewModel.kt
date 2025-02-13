package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackUseCases
import ru.pugovishnikova.example.avitotesttaskapp.util.onError
import ru.pugovishnikova.example.avitotesttaskapp.util.onSuccess



class TrackViewModel (
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
            is TrackListAction.OnTrackClick -> {}
        }
    }

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
                    }
            }
        }
    }
}