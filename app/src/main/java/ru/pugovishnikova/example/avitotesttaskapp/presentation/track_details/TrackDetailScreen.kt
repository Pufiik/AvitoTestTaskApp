package ru.pugovishnikova.example.avitotesttaskapp.presentation.track_details

import android.content.Context
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.delay
import ru.pugovishnikova.example.avitotesttaskapp.R
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.ReloadScreen
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListAction
import ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.TrackListState



@Composable
fun TrackDetailScreen(
    state: TrackListState,
    modifier: Modifier,
    onAction: (TrackListAction) -> Unit,
    onClick: () -> Unit,
    onClickButtonBack: () -> Unit
) {
    val context = LocalContext.current
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (!state.isError && state.selectedTrack!=null) {
        val track = state.selectedTrack
        var isPlaying by remember { mutableStateOf(false) }
        var currentPosition by remember { mutableIntStateOf(0) }

        LaunchedEffect(isPlaying) {
            while (isPlaying && currentPosition < track.duration) {
                delay(1000)
                currentPosition++
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onClickButtonBack) {
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
            Text(text = track.title, fontSize = 22.sp, color = Color.White)
            track.albumTitle?.let {
                Text(text = it, fontSize = 16.sp, color = Color.Gray)
            }
            Text(text = track.artistName, fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.height(30.dp))

            Slider(
                value = currentPosition.toFloat(),
                onValueChange = { newValue -> currentPosition = newValue.toInt() },
                valueRange = 0f..track.duration.toFloat(),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = formatTime(currentPosition), fontSize = 14.sp, color = Color.White)
                Text(text = formatTime(track.duration), fontSize = 14.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { /* Предыдущий трек */ }) {
                    Icon(
                        imageVector = Icons.Filled.SkipPrevious,
                        contentDescription = "Назад",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { isPlaying = !isPlaying }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = "Пауза/Воспроизведение",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { /* Следующий трек */ }) {
                    Icon(
                        imageVector = Icons.Filled.SkipNext,
                        contentDescription = "Далее",
                        tint = Color.White
                    )
                }
            }
        }
    } else if (state.tracks.isEmpty()){
        ReloadScreen { onAction(TrackListAction.OnBackClick(onClick)) }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val sec = seconds % 60
    return String.format("%02d:%02d", minutes, sec)
}
