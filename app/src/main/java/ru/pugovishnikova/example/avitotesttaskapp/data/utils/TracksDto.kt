import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TracksDTOResponse(
    @SerializedName("tracks")
    val tracks: DataDTOResponse
)

@Serializable
data class DataDTOResponse(
    @SerializedName("data")
    val data: List<TrackDto>,

    @SerializedName("total")
    val total: Int
)

@Serializable
data class TrackDto(
    val id: Long,
    val title: String,
    @SerializedName("title_short") val titleShort: String?,
    @SerializedName("title_version") val titleVersion: String? = null,
    val link: String?,
    val duration: Int?,
    val rank: Int?,
    @SerializedName("explicit_lyrics") val explicitLyrics: Boolean?,
    @SerializedName("explicit_content_lyrics") val explicitContentLyrics: Int?,
    @SerializedName("explicit_content_cover") val explicitContentCover: Int?,
    val preview: String?,
    @SerializedName("md5_image") val md5Image: String?,
    val position: Int?,
    val artist: ArtistDto?,
    val album: AlbumDto?,
    val type: String?
)

@Serializable
data class ArtistDto(
    val id: Long,
    val name: String?,
    val link: String?,
    val picture: String?,
    @SerializedName("picture_small") val pictureSmall: String?,
    @SerializedName("picture_medium") val pictureMedium: String?,
    @SerializedName("picture_big") val pictureBig: String?,
    @SerializedName("picture_xl") val pictureXl: String?,
    val radio: Boolean?,
    val trackList: String?,
    val type: String?
)

@Serializable
data class AlbumDto(
    val id: Long,
    val title: String?,
    val cover: String?,
    @SerializedName("cover_small") val coverSmall: String?,
    @SerializedName("cover_medium") val coverMedium: String?,
    @SerializedName("cover_big") val coverBig: String?,
    @SerializedName("cover_xl") val coverXl: String?,
    @SerializedName("md5_image") val md5Image: String?,
    val trackList: String?,
    val type: String?
)

