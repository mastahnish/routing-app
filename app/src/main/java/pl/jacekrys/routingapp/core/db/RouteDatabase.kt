package pl.jacekrys.routingapp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.jacekrys.routingapp.feature.route.data.local.RouteDao
import pl.jacekrys.routingapp.feature.route.data.local.model.entity.RouteEntity
import pl.jacekrys.routingapp.feature.route.data.local.model.entity.StopEntity

@Database(
    entities = [RouteEntity::class, StopEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RouteDatabase : RoomDatabase() {
    abstract fun routeDao(): RouteDao
}