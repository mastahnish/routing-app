package pl.jacekrys.routingapp.core.exception

interface ErrorWrapper {
    fun wrap(throwable: Throwable): Throwable
}