package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList

import ru.pugovishnikova.example.avitotesttaskapp.domain.util.NetworkError

sealed interface TrackListEvent {
    data class Error(val error: NetworkError): TrackListEvent
}