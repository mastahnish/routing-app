package pl.jacekrys.routingapp.feature.route.data.remote.model

import pl.jacekrys.routingapp.feature.route.domain.model.RouteDetails

data class RouteDetailsRemote(
    val distance: Int,
    val geometry: List<List<GeometryRemote>>,
    val time: Float,
) {
    fun toDomain(routeId: String) = RouteDetails(
        routeId = routeId,
        distanceInMeters = distance,
        geometry = geometry.map { geometryRemote -> geometryRemote.map { it.toDomain() } },
        time = time,
    )
}