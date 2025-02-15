package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackService

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.pugovishnikova.example.avitotesttaskapp.util.Utils

@UnstableApi
class TrackNotificationAdapter(
    private val context: Context,
    private val pendingIntent: PendingIntent?
): PlayerNotificationManager.MediaDescriptionAdapter {
    override fun getCurrentContentTitle(player: Player): CharSequence = player.mediaMetadata.displayTitle ?: Utils.getAuthorName()

    override fun createCurrentContentIntent(player: Player): PendingIntent? = pendingIntent

    override fun getCurrentContentText(player: Player): CharSequence? =
        player.mediaMetadata.albumTitle ?: Utils.getAuthorName()


    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        Glide.with(context)
            .asBitmap()
            .load(player.mediaMetadata.artworkUri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.onBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit

            })
        return null
    }
}