package ru.pugovishnikova.example.avitotesttaskapp.domain

import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Album
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Artist
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTrack
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.SearchTrackDTO
import ru.pugovishnikova.example.avitotesttaskapp.data.utils.Track


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
