package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.pugovishnikova.example.avitotesttaskapp.R


@Composable
fun TrackList (
    track: TrackUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
//        Glide.with(context)
//            .load(track.album.cover_xl) // Используем URL изображения // Заглушка на время загрузки
//            .error(R.drawable.stub_icon) // Заглушка на случай ошибки
//            .into(imageView)
        Image(

            painter = painterResource(id = R.drawable.stub_icon),
            contentDescription = track.title,
            modifier = Modifier.size(85.dp)
        )
        Column(
            modifier.weight(1f)
        ) {
            Text(
                text = track.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = track.authorName,
                fontSize = 14.sp
            )
        }
    }

}
