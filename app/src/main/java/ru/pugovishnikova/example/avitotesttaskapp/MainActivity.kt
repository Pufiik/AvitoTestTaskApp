package ru.pugovishnikova.example.avitotesttaskapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import ru.pugovishnikova.example.avitotesttaskapp.ui.theme.AvitoTestTaskAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            AvitoTestTaskAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val trackViewModel = koinViewModel<TrackViewModel>()
                    val state = trackViewModel.state.collectAsStateWithLifecycle()
                    val context = LocalContext.current
                    ObserveAsEvents(
                        events = trackViewModel.events
                    ) { event ->
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
                        state.value.selectedTrack != null -> TrackDetailScreen(
                            state = state.value,
                            modifier = Modifier.padding(innerPadding)
                        )
                        else -> TrackListScreen(
                            state = state.value,
                            onAction = trackViewModel::onAction,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
