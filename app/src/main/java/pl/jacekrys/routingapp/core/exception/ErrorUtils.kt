package pl.jacekrys.routingapp.core.exception

suspend fun <T> callOrThrow(
    errorWrapper: ErrorWrapper,
    apiCall: suspend () -> T
): T {
    return runCatching { apiCall() }
        .getOrElse { throw errorWrapper.wrap(it) }
}