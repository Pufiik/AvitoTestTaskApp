package ru.pugovishnikova.example.avitotesttaskapp.data.utils

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("readable") val readable: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("title_short") val titleShort: String?,
    @SerializedName("isrc") val isrc: String?,
    @SerializedName("link") val link: String?,
    @SerializedName("share") val share: String?,
    @SerializedName("duration") val duration: Int?,
    @SerializedName("track_position") val trackPosition: Int?,
    @SerializedName("disk_number") val diskNumber: Int?,
    @SerializedName("rank") val rank: Int?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("explicit_lyrics") val explicitLyrics: Boolean?,
    @SerializedName("explicit_content_lyrics") val explicitContentLyrics: Int?,
    @SerializedName("explicit_content_cover") val explicitContentCover: Int?,
    @SerializedName("preview") val preview: String?,
    @SerializedName("bpm") val bpm: Float?,
    @SerializedName("gain") val gain: Float?,
    @SerializedName("available_countries") val availableCountries: List<String>?,
    @SerializedName("contributors") val contributors: List<ContributorDTO>?,
    @SerializedName("md5_image") val md5Image: String?,
    @SerializedName("track_token") val trackToken: String?,
    @SerializedName("artist") val artist: Artist?,
    @SerializedName("album") val album: Album?,
    @SerializedName("type") val type: String?
)

data class ContributorDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("link") val link: String?,
    @SerializedName("share") val share: String?,
    @SerializedName("picture") val picture: String?,
    @SerializedName("picture_small") val pictureSmall: String?,
    @SerializedName("picture_medium") val pictureMedium: String?,
    @SerializedName("picture_big") val pictureBig: String?,
    @SerializedName("picture_xl") val pictureXl: String?,
    @SerializedName("radio") val radio: Boolean?,
    @SerializedName("tracklist") val trackList: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("role") val role: String?
)
