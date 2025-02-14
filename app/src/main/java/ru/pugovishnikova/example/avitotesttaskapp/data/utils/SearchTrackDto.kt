package ru.pugovishnikova.example.avitotesttaskapp.data.utils

import AlbumDto
import ArtistDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchTracksDTOResponse(
    val data: List<SearchTrackDTO>,
    val total: Int,
    val next: String? = null
)

@Serializable
data class SearchTrackDTO(
    val id: Long,
    val readable: Boolean?,
    val title: String,
    @SerialName("title_short") val titleShort: String?,
    val link: String?,
    val duration: Int?,
    val rank: Int?,
    @SerialName("explicit_lyrics") val explicitLyrics: Boolean?,
    @SerialName("explicit_content_lyrics") val explicitContentLyrics: Int?,
    @SerialName("explicit_content_cover") val explicitContentCover: Int?,
    val preview: String?,
    @SerialName("md5_image") val md5Image: String?,
    val artist: ArtistDto?,
    val album: AlbumDto?,
    val type: String?
)


