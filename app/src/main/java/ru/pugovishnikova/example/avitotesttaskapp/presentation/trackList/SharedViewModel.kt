package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedPlayerViewModel : ViewModel() {
    private val _selectedTrack = MutableStateFlow<TrackUi?>(null)
    val selectedTrack = _selectedTrack.asStateFlow()

    fun setTrack(track: TrackUi) {
        _selectedTrack.value = track
    }
}