package ru.pugovishnikova.example.avitotesttaskapp.domain.util

import retrofit2.Response
import ru.pugovishnikova.example.avitotesttaskapp.util.Result
inline fun <reified T> responseToResult(response: Response<T>?): Result<T, NetworkError> {
    return when (response?.isSuccessful) {
        true -> Result.Success(response.body()!!)
        else -> Result.Error(NetworkError.NO_INTERNET)
    }
}