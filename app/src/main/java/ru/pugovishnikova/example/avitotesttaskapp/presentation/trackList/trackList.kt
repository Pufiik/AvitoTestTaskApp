package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.pugovishnikova.example.avitotesttaskapp.R
import ru.pugovishnikova.example.avitotesttaskapp.domain.Track
import ru.pugovishnikova.example.avitotesttaskapp.ui.theme.AvitoTestTaskAppTheme

@Composable
fun TrackList(
    track: TrackUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.stub_icon),
            contentDescription = track.title,
            modifier = Modifier.size(85.dp)
        )
        Column(
            modifier.weight(1f)
        ) {
            Text(text = track.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            Text(text = track.authorName,
                fontSize = 14.sp)
        }
    }

}

@Preview
@Composable
private fun TrackListPreview() {
    AvitoTestTaskAppTheme {
        TrackList(
           track = previewTrack,
            onClick = { } ,
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

internal val previewTrack = Track(
    123456,
    "track-1",
    "author-1"
).toTrackUi()