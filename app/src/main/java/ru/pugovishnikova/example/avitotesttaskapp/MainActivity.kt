package ru.pugovishnikova.example.avitotesttaskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListScreen
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackViewModel
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
                    TrackListScreen(
                        state = state.value,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
