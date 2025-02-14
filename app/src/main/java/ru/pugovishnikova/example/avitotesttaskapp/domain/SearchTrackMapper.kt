package ru.pugovishnikova.example.avitotesttaskapp.domain

import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Contributor
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.ContributorDTO
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTrack
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTrackDTO
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.TrackInfo
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.TrackResponse


fun SearchTrackDTO.toSearchTrack(): SearchTrack {
    return SearchTrack(
        id = id,
        readable = readable,
        title = title,
        titleShort = titleShort,
        link = link,
        duration = duration,
        rank = rank,
        explicitLyrics = explicitLyrics,
        explicitContentLyrics = explicitContentLyrics,
        explicitContentCover = explicitContentCover,
        preview = preview,
        md5Image = md5Image,
        artist = artist?.toArtist(),
        album = album?.toAlbum(),
        type = type
    )
}

fun TrackResponse.toTrackInfo(): TrackInfo {
    return TrackInfo(
        id = this.id,
        readable = this.readable,
        title = this.title,
        titleShort = this.titleShort,
        isrc = this.isrc,
        link = this.link,
        share = this.share,
        duration = this.duration,
        trackPosition = this.trackPosition,
        diskNumber = this.diskNumber,
        rank = this.rank,
        releaseDate = this.releaseDate,
        explicitLyrics = this.explicitLyrics,
        explicitContentLyrics = this.explicitContentLyrics,
        explicitContentCover = this.explicitContentCover,
        preview = this.preview,
        bpm = this.bpm,
        gain = this.gain,
        availableCountries = this.availableCountries,
        contributors = this.contributors?.map{
            it.toContributor()
        },
        md5Image = this.md5Image,
        trackToken = this.trackToken,
        artist = this.artist,
        album = this.album,
        type = this.type
    )
}

fun ContributorDTO.toContributor(): Contributor {
    return Contributor(
        id = this.id,
        name = this.name,
        link = this.link,
        share = this.share,
        picture = this.picture,
        pictureSmall = this.pictureSmall,
        pictureMedium = this.pictureMedium,
        pictureBig = this.pictureBig,
        pictureXl = this.pictureXl,
        radio = this.radio,
        tracklist = this.tracklist,
        type = this.type,
        role = this.role
    )
}