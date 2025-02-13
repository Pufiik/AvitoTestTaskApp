package ru.pugovishnikova.example.avitotesttaskapp.data.utils


data class SearchTrack (
    val id: Long,
    val readable: Boolean?,
    val title: String,
    val titleShort: String?,
    val link: String?,
    val duration: Int?,
    val rank: Int?,
    val explicitLyrics: Boolean?,
    val explicitContentLyrics: Int?,
    val explicitContentCover: Int?,
    val preview: String?,
    val md5Image: String?,
    val artist: Artist?,
    val album: Album?,
    val type: String?
)