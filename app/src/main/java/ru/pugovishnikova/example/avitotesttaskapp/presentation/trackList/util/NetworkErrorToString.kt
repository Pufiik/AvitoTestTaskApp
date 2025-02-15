package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackList.util

import android.content.Context
import ru.pugovishnikova.example.avitotesttaskapp.R
import ru.pugovishnikova.example.avitotesttaskapp.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when(this) {
        NetworkError.SERIALIZATION -> R.string.serialization_error
        NetworkError.NO_INTERNET -> R.string.no_internet
        NetworkError.NO_FETCHING_DATA -> R.string.no_fetching_data
        NetworkError.LONG_REQUEST -> R.string.too_long_request
    }
    return context.getString(resId)
}