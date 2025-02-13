package ru.pugovishnikova.example.avitotesttaskapp.domain.util

import ru.pugovishnikova.example.avitotesttaskapp.util.Error

enum class NetworkError: Error {
    NO_FETCHING_DATA,
    NO_INTERNET,
    SERIALIZATION
}