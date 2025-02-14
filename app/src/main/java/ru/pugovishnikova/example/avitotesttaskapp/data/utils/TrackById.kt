package ru.pugovishnikova.example.avitotesttaskapp.data.utils

data class TrackInfo(
    val id: Long,
    val readable: Boolean,
    val title: String,
    val titleShort: String?,
    val isrc: String?,
    val link: String?,
    val share: String?,
    val duration: Int?,
    val trackPosition: Int?,
    val diskNumber: Int?,
    val rank: Int?,
    val releaseDate: String?,
    val explicitLyrics: Boolean?,
    val explicitContentLyrics: Int?,
    val explicitContentCover: Int?,
    val preview: String?,
    val bpm: Float?,
    val gain: Float?,
    val availableCountries: List<String>?,
    val contributors: List<Contributor>?,
    val md5Image: String?,
    val trackToken: String?,
    val artist: Artist?,
    val album: Album?,
    val type: String?
)

data class Contributor(
    val id: Long,
    val name: String?,
    val link: String?,
    val share: String?,
    val picture: String?,
    val pictureSmall: String?,
    val pictureMedium: String?,
    val pictureBig: String?,
    val pictureXl: String?,
    val radio: Boolean?,
    val tracklist: String?,
    val type: String?,
    val role: String?
)