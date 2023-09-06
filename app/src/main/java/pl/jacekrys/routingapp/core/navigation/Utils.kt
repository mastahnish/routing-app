package pl.jacekrys.routingapp.core.navigation

import androidx.navigation.NavController

fun NavController.getLastBackStackDestinationRoute(): String? {
    return this.currentBackStack.value.last().destination.route
}