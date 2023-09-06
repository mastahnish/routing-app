package pl.jacekrys.routingapp.feature.route.data.remote.model

import pl.jacekrys.routingapp.feature.route.domain.model.Coordinates

data class CoordRemote(
    val lat: Double,
    val lng: Double
) {
    fun toDomain() = Coordinates(
        latitude = lat.toFloat(),
        longitude = lng.toFloat()
    )
}