package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackService.TrackService
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import ru.pugovishnikova.example.avitotesttaskapp.domain.TrackUseCases
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.NetworkError
import ru.pugovishnikova.example.avitotesttaskapp.util.Utils
import ru.pugovishnikova.example.avitotesttaskapp.util.combineResults
import ru.pugovishnikova.example.avitotesttaskapp.util.onError
import ru.pugovishnikova.example.avitotesttaskapp.util.onSuccess


class TrackViewModel(
    private val trackUseCases: TrackUseCases,
    private val exoPlayer: ExoPlayer,
    private val appContext: Context
) : ViewModel() {
    private var currentTrackIndex: Int = 0
    private var trackJob: Job? = null
    private val _state = MutableStateFlow(TrackListState())
    val state = _state
        .onStart {
            getAllTracks()
            observePlayer()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            TrackListState()
        )



    private fun observePlayer() {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    _state.update {
                        it.copy(duration = (exoPlayer.duration / 1000).toInt())
                    }
                }
            }
        })

        viewModelScope.launch {
            while (true) {
                delay(1000L)
                if (exoPlayer.isPlaying) {
                    _state.update {
                        it.copy(currentPosition = (exoPlayer.currentPosition / 1000).toInt())
                    }
                }
            }
        }
    }

    fun onAction(action: TrackListAction) {
        when (action) {
            is TrackListAction.OnTrackClick -> {
                startService()
                if (state.value.selectedTrack != null && state.value.selectedTrack!!.id == action.track.id) action.navigate()
                else {
                    getTrackById(action.track.id, action.navigate)
                    _state.update {
                        it.copy(
                            selectedTrack = action.track,
                            isPlaying = true
                        )
                    }
                }
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

            is TrackListAction.OnPlayPause -> {
                togglePlayPause()
            }

            is TrackListAction.OnSeekTo -> {
                seekTo(action.position)
            }

            is TrackListAction.OnSeekToStart -> {
                restartTrack()
            }

            is TrackListAction.OnTrackLoaded -> {
                _state.update { it.copy(duration = action.duration) }
            }

            is TrackListAction.OnDownloadClick -> {
                updateDownloadTracks()
            }
            is TrackListAction.OnDownloadScreenClick -> {}
        }
    }

    private val _events = Channel<TrackListEvent>()
    val events = _events.receiveAsFlow()

    private fun getAllTracks() {
        trackJob?.cancel()
        trackJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }
            withTimeout(5000L) {
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
    }

    private fun getTrackById(id: Long, navigate: (() -> Unit)? = null) {
        trackJob?.cancel()
        trackJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }
            withTimeout(5000L) {
                withContext(Dispatchers.IO) {
                    trackUseCases.getTrackByIdUseCase(id)
                        .onSuccess { track ->
                            val trackU = track.toTrackUi()
                            val flag = (trackU.imageId == null)
                            val selectedTrack =
                                if (flag) state.value.selectedTrack else track.toTrackUi()
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isError = false,
                                    isPlaying = true,
                                    selectedTrack = selectedTrack
                                )
                            }
                            MediaItem.Builder()
                                .setUri(track.preview)
                                .setMediaMetadata(
                                    MediaMetadata.Builder()
                                        .setAlbumArtist(track.artist?.name ?: Utils.getAuthorName())
                                        .setDisplayTitle(track.title)
                                        .setSubtitle(track.titleShort ?: Utils.getEmptyString())
                                        .build()
                                ).build()

                            withContext(Dispatchers.Main) {
                                playTrack(selectedTrack?.preview!!)
                                if (navigate != null) navigate()
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
    }
    private fun updateDownloadTracks() {
        val oldList = getTracks(appContext)
        val newTracks = if (oldList.contains(state.value.selectedTrack!!)) oldList else oldList.toMutableList().apply { add(state.value.selectedTrack!!) }
        saveTracks(appContext, newTracks)
    }

    private fun searchTracksByName(query: String) {
        trackJob?.cancel()
        trackJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }
            withTimeout(5000L) {
                withContext(Dispatchers.IO) {
                    val tracksWithName =
                        async { trackUseCases.searchTracksUseCases.invoke("track:\"$query\"") }
                    val tracksWithArtist =
                        async { trackUseCases.searchTracksUseCases.invoke("artist:\"$query\"") }
                    listOf(tracksWithName, tracksWithArtist)
                        .awaitAll()
                        .combineResults()
                        .onSuccess { tracks ->
                            if (tracks.isEmpty()) {
                                _events.send(TrackListEvent.Error(NetworkError.NO_FETCHING_DATA))
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = false
                                    )
                                }
                            } else {
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        isError = false,
                                        tracks = tracks.map { track -> track.toTrackUi() }
                                    )
                                }
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
    }

    private fun seekTo(position: Int) {
        exoPlayer.seekTo((position * 1000).toLong())
        _state.update { it.copy(currentPosition = position) }
    }

    private fun restartTrack() {
        exoPlayer.seekTo(0)
        exoPlayer.playWhenReady = true
        _state.update { it.copy(currentPosition = 0) }
    }

    private fun playNextTrack() {
        if (currentTrackIndex < state.value.tracks.size - 1) {
            currentTrackIndex++
            updateTrack()
        }
    }

    private fun playPreviousTrack() {
        exoPlayer.seekTo(0)
        if (currentTrackIndex > 0) {
            currentTrackIndex--
            updateTrack()
        }
    }

    private fun updateTrack() {
        val newTrack = state.value.tracks[currentTrackIndex]
        getTrackById(newTrack.id)
        _state.update { it.copy(selectedTrack = newTrack, currentPosition = 0) }
        playTrack(newTrack.preview)
    }

    private fun playTrack(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        _state.update {
            it.copy(
                isPlaying = true,
                currentPosition = 0
            )
        }
    }


    private fun stopForegroundService() {
        val intent = Intent(appContext, TrackService::class.java)
        appContext.stopService(intent)
    }


    private fun togglePlayPause() {
        val isPlaying = !_state.value.isPlaying
        exoPlayer.playWhenReady = isPlaying
        _state.update { it.copy(isPlaying = isPlaying) }
    }

    @OptIn(UnstableApi::class)
    private fun startService() {
        if (!state.value.isServicePlaying) {
            val intent = Intent(appContext, TrackService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                appContext.startForegroundService(intent)
            } else {
                appContext.startService(intent)
            }
            _state.update { it.copy(isServicePlaying = true) }
        }
    }

    private fun saveTracks(context: Context, tracks: List<TrackUi>) {
        val sharedPreferences = context.getSharedPreferences(Utils.getTracksPrefsString(), Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(tracks)
        editor.putString(Utils.getTracksListString(), json)
        editor.apply()
    }

    private fun getTracks(context: Context): List<TrackUi> {
        val sharedPreferences = context.getSharedPreferences(Utils.getTracksPrefsString(), Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(Utils.getTracksListString(), Utils.getSquareString())
        val type = object : TypeToken<List<TrackUi>>() {}.type
        return Gson().fromJson(json, type)
    }


    override fun onCleared() {
        super.onCleared()
        stopForegroundService()
        exoPlayer.release()
    }
}