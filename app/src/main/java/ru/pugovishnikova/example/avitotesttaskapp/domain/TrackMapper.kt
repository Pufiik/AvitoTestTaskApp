package ru.pugovishnikova.example.avitotesttaskapp.domain

import AlbumDto
import ArtistDto
import TrackDto
import ru.pugovishnikova.example.avitotesttaskapp.domain.model.Album
import ru.pugovishnikova.example.avitotesttaskapp.domain.model.Artist
import ru.pugovishnikova.example.avitotesttaskapp.domain.model.Track

fun TrackDto.toTrack(): Track {
    return Track(
        id = id,
        title = title,
        titleShort = titleShort,
        titleVersion = titleVersion,
        link = link,
        duration = duration,
        rank = rank,
        explicitLyrics = explicitLyrics,
        explicitContentLyrics = explicitContentLyrics,
        explicitContentCover = explicitContentCover,
        preview = preview,
        md5Image = md5Image,
        position = position,
        artist = artist?.toArtist(),
        album = album?.toAlbum(),
        type = type
    )
}

fun ArtistDto.toArtist(): Artist {
    return Artist(
        id = id,
        name = name,
        link = link,
        picture = picture,
        pictureSmall = pictureSmall,
        pictureMedium = pictureMedium,
        pictureBig = pictureBig,
        pictureXl = pictureXl,
        radio = radio,
        trackList = trackList,
        type = type
    )
}

fun AlbumDto.toAlbum(): Album {
    return Album(
        id = id,
        title = title,
        cover = cover,
        coverSmall = coverSmall,
        coverMedium = coverMedium,
        coverBig = coverBig,
        coverXl = coverXl,
        md5Image = md5Image,
        trackList = trackList,
        type = type
    )
}

// Доменные модели
