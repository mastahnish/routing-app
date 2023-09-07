package pl.jacekrys.routingapp.feature.route.data.local.model

import androidx.room.Relation
import pl.jacekrys.routingapp.feature.route.data.local.model.entity.RouteDetailsCoordinatesEntity
import pl.jacekrys.routingapp.feature.route.domain.model.Coordinates
import pl.jacekrys.routingapp.feature.route.domain.model.RouteDetails

data class RouteDetailsCached(
    val routeId: String,
    val distanceInMeters: Int,
    val time: Float,
    @Relation(
        parentColumn = "routeId",
        entityColumn = "routeId"
    )
    val geometry: List<RouteDetailsCoordinatesEntity>,
) {
    fun toDomain() = RouteDetails(
        routeId = routeId,
        distanceInMeters = distanceInMeters,
        time = time,
        geometry = geometry.map { Coordinates(it.latitude, it.longitude) }
    )
}
