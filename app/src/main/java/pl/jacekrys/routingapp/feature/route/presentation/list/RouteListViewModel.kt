package pl.jacekrys.routingapp.feature.route.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.jacekrys.routingapp.core.base.Resource
import pl.jacekrys.routingapp.core.navigation.Navigator
import pl.jacekrys.routingapp.core.navigation.Screen
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.usecase.GetRoutesListUseCase
import pl.jacekrys.routingapp.feature.route.presentation.RoutesErrorMapper
import timber.log.Timber

class RouteListViewModel(
    private val getRoutesListUseCase: GetRoutesListUseCase,
    private val routesErrorMapper: RoutesErrorMapper,
    private val navigator: Navigator
) : ViewModel() {
    private val _state by lazy { MutableStateFlow(RouteListState()) }
    val state = _state.asStateFlow()

    private var routes: List<Route> = listOf()
    fun getRoutes() {
        viewModelScope.launch {
            _state.value = state.value.copy(isListLoading = true)
            when (val result = getRoutesListUseCase()) {
                is Resource.Success -> {
                    routes = result.data
                    _state.value = _state.value.copy(
                        errorInfo = null
                    )
                    filterResults()
                }

                is Resource.Error -> {
                    Timber.e(result.error, "Error while getting routes")
                    _state.value = _state.value.copy(
                        errorInfo = routesErrorMapper.map(result.error)
                    )
                }
            }
            _state.value = state.value.copy(isListLoading = false)
        }
    }

    fun updateSearchText(text: String) {
        _state.value = _state.value.copy(searchText = text)
        filterResults()
    }

    fun chooseRoute(route: Route) {
        navigator.navigateTo(Screen.RouteDetails, route.id)
    }

    private fun filterResults() {
        _state.value = _state.value.copy(
            routes = routes.filter { it.name.contains(_state.value.searchText, true) }
        )
    }
}