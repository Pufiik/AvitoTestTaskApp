package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.pugovishnikova.example.avitotesttaskapp.util.Utils


@Composable
fun TrackListScreen(
    state: TrackListState,
    onAction: (TrackListAction) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (!state.isError) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SearchInputField { query ->
                onAction(TrackListAction.OnSearchButtonClick(query))
            }

            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.tracks) { trackUi ->
                    TrackList(
                        track = trackUi,
                        onClick = { onAction(TrackListAction.OnTrackClick(trackUi, onClick)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()

                }
            }
        }
    } else {
        ReloadScreen { onAction(TrackListAction.OnReloadButtonClick) }
    }
}


@Composable
fun DownloadScreen(
    state: TrackListState,
    onAction: (TrackListAction) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        onAction(TrackListAction.OnDownloadScreenClick)
    }

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (!state.isError) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SearchInputField { query ->
                onAction(TrackListAction.OnSearchButtonClick(query))
            }
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.downloadTracks) { trackUi ->
                    TrackList(
                        track = trackUi,
                        onClick = { onAction(TrackListAction.OnTrackClick(trackUi, onClick)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()

                }
            }
        }
    }
}


@Composable
fun ReloadScreen(onReloadClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onReloadClick) {
            Text(text = Utils.getReloadString())
        }
    }
}

@Composable
fun SearchInputField(
    onSearch: (String) -> Unit
) {
    var text by remember { mutableStateOf(Utils.getEmptyString()) }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.weight(1f),
            label = { Text(Utils.getInputText()) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onSearch(text.lowercase()) }) {
            Text(Utils.getSearch())
        }
    }
}


