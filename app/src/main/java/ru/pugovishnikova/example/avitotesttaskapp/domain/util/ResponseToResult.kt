package ru.pugovishnikova.example.avitotesttaskapp.domain.util

import retrofit2.Response
import ru.pugovishnikova.example.avitotesttaskapp.util.Result

inline fun <reified T> responseToResult(
    response: Response<T>?,
    isException: Boolean = false,
    typeException: NetworkError? = null
): Result<T, NetworkError> {
    if (response?.isSuccessful == true) return Result.Success(response.body()!!)
    else if (isException) {
        return Result.Error(typeException!!)
    }
    return Result.Error(NetworkError.NO_FETCHING_DATA)
}