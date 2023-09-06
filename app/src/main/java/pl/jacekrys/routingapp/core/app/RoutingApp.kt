package pl.jacekrys.routingapp.core.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import pl.jacekrys.routingapp.core.di.koinInjector
import timber.log.Timber

class RoutingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
        Timber.plant(Timber.DebugTree())
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@RoutingApp)
            modules(koinInjector)
        }
    }
}