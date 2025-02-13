package ru.pugovishnikova.example.avitotesttaskapp.domain.model
data class Track(
    val id: Long,
    val title: String,
    val titleShort: String?,
    val titleVersion: String?,
    val link: String?,
    val duration: Int?,
    val rank: Int?,
    val explicitLyrics: Boolean?,
    val explicitContentLyrics: Int?,
    val explicitContentCover: Int?,
    val preview: String?,
    val md5Image: String?,
    val position: Int?,
    val artist: Artist? = null,
    val album: Album?,
    val type: String?
)

data class Artist(
    val id: Long?,
    val name: String?,
    val link: String?,
    val picture: String?,
    val pictureSmall: String?,
    val pictureMedium: String?,
    val pictureBig: String?,
    val pictureXl: String?,
    val radio: Boolean?,
    val trackList: String?,
    val type: String?
)

data class Album(
    val id: Long?,
    val title: String?,
    val cover: String?,
    val coverSmall: String?,
    val coverMedium: String?,
    val coverBig: String?,
    val coverXl: String?,
    val md5Image: String?,
    val trackList: String?,
    val type: String?
)