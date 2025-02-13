package ru.pugovishnikova.example.avitotesttaskapp.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListAction
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListEvent
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListScreen
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.util.ObserveAsEvents
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.util.toString
import ru.pugovishnikova.example.avitotesttaskapp.presentation.track_details.TrackDetailScreen


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
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

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                TrackListScreen(
                    state = state.value,
                    onAction = { action ->
                        trackViewModel.onAction(action)
                        when (action) {
                            is TrackListAction.OnTrackClick -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                TrackDetailScreen(
                    state = state.value,
                    modifier = Modifier
                ) { navigator.navigateBack() }
            }
        },
        modifier = modifier
    )
}