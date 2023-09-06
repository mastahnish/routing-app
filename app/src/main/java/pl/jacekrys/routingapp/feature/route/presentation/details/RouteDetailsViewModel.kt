package pl.jacekrys.routingapp.feature.route.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.jacekrys.routingapp.core.base.Resource
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.usecase.GetRouteUseCase
import pl.jacekrys.routingapp.feature.route.presentation.model.RouteDisplayable
import timber.log.Timber

class RouteDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getRouteUseCase: GetRouteUseCase
) : ViewModel() {
    private val _state by lazy { MutableStateFlow(RouteDetailsState()) }
    val state = _state.asStateFlow()
    private val routeId: String = checkNotNull(savedStateHandle["id"])

    fun getRouteDetails() {
        viewModelScope.launch {
            when (val result = getRouteUseCase(routeId)) {
                is Resource.Success -> handleRouteSuccess(result.data)
                is Resource.Error -> {
                    Timber.e(result.error, "Error while getting route")
                }
            }
        }
    }

    private fun handleRouteSuccess(route: Route) {
        _state.value = state.value.copy(route = RouteDisplayable(route))
    }
}