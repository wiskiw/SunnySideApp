package dev.wiskiw.common.data.model

sealed interface Response<T> {
    data class Success<T>(val data: T) : Response<T>
    data class Failure<T>(val error: Throwable) : Response<T>
}
