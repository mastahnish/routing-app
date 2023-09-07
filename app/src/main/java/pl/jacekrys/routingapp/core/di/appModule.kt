package pl.jacekrys.routingapp.core.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.jacekrys.routingapp.core.db.RouteDatabase
import pl.jacekrys.routingapp.core.navigation.Navigator
import pl.jacekrys.routingapp.core.network.NetworkStateProvider
import pl.jacekrys.routingapp.core.network.NetworkStateProviderImpl

val appModule = module {
    singleOf(::Navigator)
    single {
        Room.databaseBuilder(
            androidContext(),
            RouteDatabase::class.java,
            "route-db"
        ).build()
    }

    single {
        get<RouteDatabase>().routeDao()
    }

    factory { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    single<NetworkStateProvider> { NetworkStateProviderImpl(get()) }
}