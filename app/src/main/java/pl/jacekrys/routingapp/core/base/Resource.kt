package pl.jacekrys.routingapp.core.base

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val error: Throwable) : Resource<T>
}