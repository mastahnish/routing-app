package pl.jacekrys.routingapp.core.network

interface NetworkStateProvider {
    fun isNetworkAvailable(): Boolean
}