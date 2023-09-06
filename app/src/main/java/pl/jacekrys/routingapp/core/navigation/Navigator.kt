package pl.jacekrys.routingapp.core.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class Navigator() {

    companion object {
        val START_DESTINATION = Screen.RoutesList
    }

    private val _navigationEvents =
        Channel<NavigationEvent>(Channel.RENDEZVOUS)
    val navigationEvents = _navigationEvents.receiveAsFlow()

    fun navigateTo(screen: Screen) {
        _navigationEvents.trySend(NavigationEvent.Destination(screen))
    }

    fun navigateBack() {
        _navigationEvents.trySend(NavigationEvent.Back)
    }
}

sealed interface NavigationEvent {
    data class Destination(
        val screen: Screen
    ) : NavigationEvent

    object Back : NavigationEvent
}

sealed class Screen(val route: String) {
    object RoutesList : Screen("routes_list")
    object RouteDetails : Screen("route_details")
}