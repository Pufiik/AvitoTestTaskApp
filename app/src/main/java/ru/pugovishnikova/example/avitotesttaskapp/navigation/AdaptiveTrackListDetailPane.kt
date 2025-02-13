package ru.pugovishnikova.example.avitotesttaskapp.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListEvent
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListScreen
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.util.ObserveAsEvents
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.util.toString
import ru.pugovishnikova.example.avitotesttaskapp.presentation.track_details.TrackDetailScreen

@Composable
fun AdaptiveTrackListDetailPane(
    trackViewModel: TrackViewModel = koinViewModel(),
    modifier: Modifier
) {
    val state = trackViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = trackViewModel.events) { event ->
        when (event) {
            is TrackListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    when {
        state.value.selectedTrack != null -> {
            TrackDetailScreen(
                state = state.value,
                modifier = modifier
            ) {}
        }
        else -> {
            TrackListScreen(
                state = state.value,
                onAction = { action ->
                    trackViewModel.onAction(action)
                }
            )
        }
    }
}
