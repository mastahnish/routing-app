package pl.jacekrys.routingapp.feature.route.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository

class RouteListViewModel(
    private val routeRepository: RouteRepository
) : ViewModel() {
    private val _state by lazy { MutableStateFlow(RouteListState()) }
    val state = _state.asStateFlow()

    private var routes: List<Route> = listOf()
    fun getRoutes() {
        viewModelScope.launch {
            val routesResult = routeRepository.getRoutes()
            routes = routesResult
            filterResults()
        }
    }

    fun updateSearchText(text: String) {
        _state.value = _state.value.copy(searchText = text)
        filterResults()
    }

    private fun filterResults() {
        _state.value = _state.value.copy(
            routes = routes.filter { it.name.contains(_state.value.searchText, true) }
        )
    }
}