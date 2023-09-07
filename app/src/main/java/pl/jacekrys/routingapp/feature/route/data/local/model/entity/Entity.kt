package pl.jacekrys.routingapp.feature.route.data.local.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routes")
data class RouteEntity(
    @PrimaryKey val id: String,
    val name: String
)

@Entity(tableName = "stops")
data class StopEntity(
    @PrimaryKey val id: String,
    val routeId: String,
    @Embedded val coordinates: CoordinatesEntity
)

@Entity("route_coordinates")
data class CoordinatesEntity(
    val latitude: Float,
    val longitude: Float
)

@Entity("routes_details")
data class RouteDetailsEntity(
    @PrimaryKey val routeId: String,
    val distanceInMeters: Int,
    val time: Float,
)

@Entity("route_details_coordinates")
data class RouteDetailsCoordinatesEntity(
    val routeId: String,
    val latitude: Float,
    val longitude: Float
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}