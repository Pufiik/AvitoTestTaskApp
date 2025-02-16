package ru.pugovishnikova.example.avitotesttaskapp.domain

import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetAllTracksUseCase
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetTrackByIdUseCase
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.SearchTracksUseCase


data class TrackUseCases (
    val getAllTracksUseCase: GetAllTracksUseCase,
    val searchTracksUseCases: SearchTracksUseCase,
    val getTrackByIdUseCase: GetTrackByIdUseCase,

)