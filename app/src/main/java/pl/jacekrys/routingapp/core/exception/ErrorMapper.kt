package pl.jacekrys.routingapp.core.exception

interface ErrorMapper {
    fun map(error: Throwable): String
}