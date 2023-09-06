package pl.jacekrys.routingapp.feature.route.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository

class RouteListViewModel(
    private val routeRepository: RouteRepository
) : ViewModel() {
    private val _state by lazy { MutableStateFlow(RouteListState()) }
    val state = _state.asStateFlow()

    fun getRoutes() {
        viewModelScope.launch {
            val routes = routeRepository.getRoutes()
            _state.value = RouteListState(routes = routes)
        }
    }
}