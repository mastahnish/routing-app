package pl.jacekrys.routingapp.feature.route.presentation.list

import pl.jacekrys.routingapp.feature.route.domain.model.Route

data class RouteListState(
    val routes: List<Route> = listOf(),
    val searchText: String = "",
    val isListLoading: Boolean = false,
    val errorInfo: String? = null
)