package pl.jacekrys.routingapp.feature.route.domain.model

data class RouteDetails(
    val mode: Mode,
    val distance: Float,
    val distanceUnits: DistanceUnit,
    val time: Float,
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