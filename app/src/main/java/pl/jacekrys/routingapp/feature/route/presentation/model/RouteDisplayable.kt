package pl.jacekrys.routingapp.feature.route.presentation.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.model.Stop

data class RouteDisplayable(
    val id: String,
    val name: String,
    val stops: List<Stop>
) {
    constructor(route: Route) : this(
        id = route.id,
        name = route.name,
        stops = route.stops ?: emptyList()
    )

    fun toDomain() = Route(
        id = id,
        name = name,
        stops = stops
    )

    fun getCameraBounds(): LatLngBounds {
        val minLat = stops?.minOfOrNull { it.coordinates.latitude.toDouble() } ?: 0.0
        val minLng = stops?.minOfOrNull { it.coordinates.longitude.toDouble() } ?: 0.0
        val maxLat = stops?.maxOfOrNull { it.coordinates.latitude.toDouble() } ?: 0.0
        val maxLng = stops?.maxOfOrNull { it.coordinates.longitude.toDouble() } ?: 0.0
        return LatLngBounds(
            LatLng(minLat, minLng),
            LatLng(maxLat, maxLng)
        )
    }
}
