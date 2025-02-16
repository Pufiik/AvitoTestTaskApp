package ru.pugovishnikova.example.avitotesttaskapp.presentation.track_details

import android.content.Context
import android.widget.ImageView
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.pugovishnikova.example.avitotesttaskapp.R
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.ReloadScreen
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListAction
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListState
import ru.pugovishnikova.example.avitotesttaskapp.util.Utils


@Composable
fun TrackDetailScreen(
    state: TrackListState,
    modifier: Modifier,
    onClick: () -> Unit,
    onAction: (TrackListAction) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    if (state.selectedTrack != null && !state.isError) {
        val track = state.selectedTrack
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { onAction(TrackListAction.OnBackClick(onBackClick)) }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = R.string.push_back.toString(),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            AndroidView(
                factory = { ctx: Context ->
                    ImageView(ctx).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { imageView ->
                    Glide.with(context)
                        .load(track.imageId)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.stub_icon)
                                .error(R.drawable.stub_icon)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                        )
                        .into(imageView)
                },
                modifier = Modifier.size(85.dp, 85.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = track.title,
                fontSize = 22.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            track.albumTitle?.let {
                Text(text = it, fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
            }
            Text(
                text = track.artistName,
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))

            Slider(
                value = state.currentPosition.toFloat(),
                onValueChange = { newValue -> onAction(TrackListAction.OnSeekTo(newValue.toInt())) },
                onValueChangeFinished = {
                    if (state.currentPosition == 0) onAction(TrackListAction.OnSeekToStart)
                },
                valueRange = 0f..(track.duration.toFloat()),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(state.currentPosition),
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(text = formatTime(track.duration), fontSize = 14.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    onAction(TrackListAction.OnPrevClick)
                }) {
                    Icon(
                        imageVector = Icons.Filled.SkipPrevious,
                        contentDescription = Utils.getPrevTrackString(),
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    onAction(TrackListAction.OnPlayPause)
                }) {
                    Icon(
                        imageVector = if (state.isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = Utils.getPlayPauseString(),
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    onAction(TrackListAction.OnNextClick)
                }) {
                    Icon(
                        imageVector = Icons.Filled.SkipNext,
                        contentDescription = Utils.getNextTrackString(),
                        tint = Color.White
                    )
                }
                Button(onClick = {onAction(TrackListAction.OnDownloadClick)}) {
                    Text("Скачать трек")
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Скачанные треки", fontSize = 18.sp )

    } else if (state.isError) {
        ReloadScreen { onAction(TrackListAction.OnBackClick(onClick)) }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val sec = seconds % 60
    return "%d:%02d".format(minutes, sec)
}
