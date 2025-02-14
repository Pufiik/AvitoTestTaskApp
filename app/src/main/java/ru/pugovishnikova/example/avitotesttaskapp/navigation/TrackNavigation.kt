package ru.pugovishnikova.example.avitotesttaskapp.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListEvent
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListScreen
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.util.ObserveAsEvents
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.util.toString
import ru.pugovishnikova.example.avitotesttaskapp.presentation.track_details.TrackDetailScreen
import ru.pugovishnikova.example.avitotesttaskapp.util.Utils

@Composable
fun AdaptiveTrackListDetailPane(
    trackViewModel: TrackViewModel = koinViewModel(),
    modifier: Modifier
) {
    val state = trackViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val controller = rememberNavController()
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

    NavHost(
        navController = controller,
        startDestination = Utils.getTrackListScreen()
    ) {
        composable(Utils.getTrackListScreen()) {
            TrackListScreen(
                state = state.value,
                onAction = { action ->
                    trackViewModel.onAction(action)
                },
                onClick = { controller.navigate(Utils.getTrackDetailScreen()) }
            )
        }
        composable(Utils.getTrackDetailScreen()) {
            TrackDetailScreen(
                state = state.value,
                onAction = { action ->
                    trackViewModel.onAction(action)
                },
                onClick = { controller.navigate(Utils.getTrackListScreen()) },
                modifier = modifier
            ) { controller.navigate(Utils.getTrackListScreen()) }
        }
    }
}
