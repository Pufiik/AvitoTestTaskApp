package ru.pugovishnikova.example.avitotesttaskapp.util

typealias DomainError = Error
typealias EmptyResult<E> = Result<Unit, E>

sealed interface Result<out T, out E : DomainError> {
    data class Success<out T>(val data: T) : Result<T, Nothing>
    data class Error<out E : DomainError>(val error: E) : Result<Nothing, E>
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this){
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Success -> {
            action(data)
            this
        }
        is Result.Error -> this
    }
}

inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Success -> this
        is Result.Error -> {
            action(error)
            this
        }
    }
}

fun <T, E : Error> List<Result<List<T>, E>>.combineResults(): Result<List<T>, E> {
    val successResults = this.filterIsInstance<Result.Success<List<T>>>()
    val errorResults = this.filterIsInstance<Result.Error<E>>()

    return if (errorResults.isNotEmpty()) {
        Result.Error(errorResults.first().error) // Возвращаем первую ошибку
    } else {
        Result.Success(successResults.flatMap { it.data }) // Объединяем списки
    }
}
