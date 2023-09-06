package pl.jacekrys.routingapp.feature.route.data.remote.model

import pl.jacekrys.routingapp.feature.route.domain.model.Coordinates

data class GeometryRemote(
    val lat: Double,
    val lon: Double
){
    fun toDomain() = Coordinates(
        latitude = lat.toFloat(),
        longitude = lon.toFloat()
    )
}