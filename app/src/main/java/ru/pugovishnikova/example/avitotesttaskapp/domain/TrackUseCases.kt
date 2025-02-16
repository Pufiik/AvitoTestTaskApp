package ru.pugovishnikova.example.avitotesttaskapp.domain

//import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.DownloadTrackUseCase
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetAllTracksUseCase
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.GetTrackByIdUseCase
import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.SearchTracksUseCase
//import ru.pugovishnikova.example.avitotesttaskapp.domain.usecases.UpdateTracksUseCase

data class TrackUseCases (
    val getAllTracksUseCase: GetAllTracksUseCase,
    val searchTracksUseCases: SearchTracksUseCase,
    val getTrackByIdUseCase: GetTrackByIdUseCase,
//    val downloadTrackUseCase: DownloadTrackUseCase,
//    val updateTracksUseCase: UpdateTracksUseCase
)