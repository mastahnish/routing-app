package pl.jacekrys.routingapp.feature.route.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import pl.jacekrys.routingapp.feature.route.data.local.model.entity.RouteEntity
import pl.jacekrys.routingapp.feature.route.data.local.model.entity.StopEntity
import pl.jacekrys.routingapp.feature.route.domain.model.Coordinates
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.model.Stop

data class RouteCached(
    @Embedded val route: RouteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "routeId"
    ) val stops: List<StopEntity>,
) {
    fun toDomain(): Route = Route(
        id = route.id,
        name = route.name,
        stops = stops.map { stopEntity ->
            Stop(stopEntity.id, stopEntity.coordinates.let {
                Coordinates(it.latitude, it.longitude)
            })
        }
    )
}