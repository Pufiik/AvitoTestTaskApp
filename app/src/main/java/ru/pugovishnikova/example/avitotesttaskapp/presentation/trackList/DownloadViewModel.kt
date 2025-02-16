package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackUseCases

class DownloadViewModel(
    private val trackUseCases: TrackUseCases,
    private val sharedPlayerViewModel: SharedPlayerViewModel,
    private val appContext: Context
) : ViewModel() {
    private val _state = MutableStateFlow(TrackListState())
    val state = _state
        .onStart { getDownloadedTracks(appContext) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            TrackListState()
        )


    fun onAction(action: TrackListAction) {
        when (action) {
            is TrackListAction.OnTrackClick -> {
                sharedPlayerViewModel.setTrack(action.track)
                action.navigate()
            }

            is TrackListAction.OnSearchButtonClick -> {}

            is TrackListAction.OnReloadButtonClick -> {}

            is TrackListAction.OnBackClick -> {}

            is TrackListAction.OnNextClick -> {}

            is TrackListAction.OnPrevClick -> {}

            is TrackListAction.OnPlayPause -> {}

            is TrackListAction.OnSeekTo -> {}

            is TrackListAction.OnSeekToStart -> {}

            is TrackListAction.OnTrackLoaded -> {}

            is TrackListAction.OnDownloadClick -> {}
            is TrackListAction.OnDownloadScreenClick -> {
                getDownloadedTracks(appContext)
            }
        }
    }


    private fun getDownloadedTracks(context: Context) {
        viewModelScope.launch {
            val tracks = getTracks(context)
            _state.update {
                it.copy(
                    downloadTracks = tracks
                )
            }
        }
    }

    private suspend fun getTracks(context: Context): List<TrackUi> =
        withContext(Dispatchers.IO) {
            val sharedPreferences =
                context.getSharedPreferences("tracks_prefs", Context.MODE_PRIVATE)
            val json = sharedPreferences.getString("tracks_list", "[]")
            val type = object : TypeToken<List<TrackUi>>() {}.type
            Gson().fromJson(json, type)
    }
}