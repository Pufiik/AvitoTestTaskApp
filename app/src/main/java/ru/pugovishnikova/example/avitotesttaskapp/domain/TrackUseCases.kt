package ru.pugovishnikova.example.avitotesttaskapp.domain

import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetAllTracksUseCase

data class TrackUseCases (
    val getAllTracksUseCase: GetAllTracksUseCase
)