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