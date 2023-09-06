package pl.jacekrys.routingapp.feature.route.domain.model

data class RouteDetails(
    val routeId: String,
    val distanceInMeters: Int,
    val time: Float,
    val geometry: List<List<Coordinates>>,
)
