package pl.jacekrys.routingapp.feature.route.data.remote.model

import pl.jacekrys.routingapp.feature.route.domain.model.Route

data class RouteRemote(
    val id: String,
    val name: String,
    val stops: List<StopRemote> = emptyList()
) {
    fun toDomain(): Route = Route(
        id, name, stops.map { stopRemote -> stopRemote.toDomain() }
    )
}