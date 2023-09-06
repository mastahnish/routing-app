package pl.jacekrys.routingapp.core.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import pl.jacekrys.routingapp.core.di.koinInjector

class RoutingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@RoutingApp)
            modules(koinInjector)
        }
    }
}