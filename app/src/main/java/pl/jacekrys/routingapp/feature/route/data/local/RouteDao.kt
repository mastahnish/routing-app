package pl.jacekrys.routingapp.feature.route.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import pl.jacekrys.routingapp.feature.route.data.local.model.RouteCached
import pl.jacekrys.routingapp.feature.route.data.local.model.entity.CoordinatesEntity
import pl.jacekrys.routingapp.feature.route.data.local.model.entity.RouteEntity
import pl.jacekrys.routingapp.feature.route.data.local.model.entity.StopEntity
import pl.jacekrys.routingapp.feature.route.domain.model.Route

@Dao
interface RouteDao {

    @Query("SELECT * FROM routes")
    @Transaction
    suspend fun getRoutes(): List<RouteCached>

    @Transaction
    @Query("SELECT * FROM routes WHERE id = :routeId")
    suspend fun getRouteById(routeId: String): RouteCached

    @Transaction
    suspend fun saveRoutes(vararg route: Route) {
        route.forEach {
            insertRouteEntities(RouteEntity(it.id, it.name))
            if (it.stops != null)
                insertStopEntities(
                    *it.stops.map { stop ->
                        StopEntity(
                            stop.id,
                            it.id,
                            CoordinatesEntity(stop.coordinates.latitude, stop.coordinates.longitude)
                        )
                    }.toTypedArray()
                )
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRouteEntities(vararg route: RouteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStopEntities(vararg stop: StopEntity)
}