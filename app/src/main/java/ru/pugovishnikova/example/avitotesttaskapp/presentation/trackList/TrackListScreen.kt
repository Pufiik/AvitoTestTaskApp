package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.pugovishnikova.example.avitotesttaskapp.ui.theme.AvitoTestTaskAppTheme

@Composable
fun TrackListScreen (
    state: TrackListState,
    modifier: Modifier = Modifier,
){
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.tracks) { trackUi ->
                    TrackList(
                        track = trackUi,
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()

            }
        }
    }
}

@Preview
@Composable
private fun TrackListScreenPreview() {
    AvitoTestTaskAppTheme {
        TrackListScreen(
            state = TrackListState(
                tracks = (1 .. 20).map {
                    previewTrack
                }
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}