package pl.jacekrys.routingapp.feature.route.domain.model

import pl.jacekrys.routingapp.feature.route.data.remote.model.GeometryRemote

data class RouteDetails(
    val routeId: String,
    val mode: Mode,
    val distance: Float,
    val distanceUnits: DistanceUnit,
    val time: Float,
    val geometry: List<List<Coordinates>>
)

enum class Mode {
    DRIVE
}

enum class DistanceUnit {
    METERS
}

data class Waypoint(
    val id: Int,
    val location: Coordinates
)
