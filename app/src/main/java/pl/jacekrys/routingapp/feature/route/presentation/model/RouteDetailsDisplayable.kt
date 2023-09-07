package pl.jacekrys.routingapp.feature.route.presentation.model

import com.google.android.gms.maps.model.LatLng
import pl.jacekrys.routingapp.feature.route.domain.model.Coordinates
import pl.jacekrys.routingapp.feature.route.domain.model.RouteDetails

data class RouteDetailsDisplayable(
    val routeId: String,
    val distanceInMeters: Int,
    val time: Float,
    val geometry: List<Coordinates>,
) {
    constructor(routeDetails: RouteDetails) : this(
        routeDetails.routeId,
        routeDetails.distanceInMeters,
        routeDetails.time,
        routeDetails.geometry
    )

    fun toDomain(): RouteDetails {
        return RouteDetails(
            routeId,
            distanceInMeters,
            time,
            geometry
        )
    }

    fun getFormattedDuration(): String = "${time.div(60).toInt()}:${time.toInt() % 60} min"

    fun getFormattedDistance(): String = String.format("%.2f km", distanceInMeters.div(1000f))

    fun getCoordinatesAsLatLng(): List<LatLng> {
        return geometry.map { coordinate ->
            LatLng(coordinate.latitude.toDouble(), coordinate.longitude.toDouble())
        }
    }
}
