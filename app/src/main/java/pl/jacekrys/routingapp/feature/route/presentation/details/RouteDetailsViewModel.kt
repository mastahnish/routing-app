package pl.jacekrys.routingapp.feature.route.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.jacekrys.routingapp.core.base.Resource
import pl.jacekrys.routingapp.core.navigation.Navigator
import pl.jacekrys.routingapp.feature.route.domain.model.Route
import pl.jacekrys.routingapp.feature.route.domain.model.RouteDetails
import pl.jacekrys.routingapp.feature.route.domain.usecase.GetRouteDetailsUseCase
import pl.jacekrys.routingapp.feature.route.domain.usecase.GetRouteUseCase
import pl.jacekrys.routingapp.feature.route.presentation.model.RouteDetailsDisplayable
import pl.jacekrys.routingapp.feature.route.presentation.model.RouteDisplayable
import timber.log.Timber

class RouteDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getRouteUseCase: GetRouteUseCase,
    private val getRouteDetailsUseCase: GetRouteDetailsUseCase,
    private val navigator: Navigator
) : ViewModel() {
    private val _state by lazy { MutableStateFlow(RouteDetailsState()) }
    val state = _state.asStateFlow()
    private val routeId: String = checkNotNull(savedStateHandle["id"])

    fun getRouteDetails() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            when (val result = getRouteUseCase(routeId)) {
                is Resource.Success -> handleRouteSuccess(result.data)
                is Resource.Error -> {
                    _state.value = state.value.copy(routeError = true, isLoading = false)
                }
            }
        }
    }

    private fun handleRouteDetailsSuccess(routeDetails: RouteDetails) {
        _state.value = state.value.copy(
            routeDetails = RouteDetailsDisplayable(routeDetails),
            isLoading = false
        )
    }

    private fun handleRouteSuccess(route: Route) {
        _state.value = state.value.copy(route = RouteDisplayable(route))
        viewModelScope.launch {
            when (val result = getRouteDetailsUseCase(route)) {
                is Resource.Success -> {
                    handleRouteDetailsSuccess(result.data)
                }

                is Resource.Error -> {
                    Timber.e(result.error, "Error while getting route details")
                    _state.value = state.value.copy(routeDetailsError = true, isLoading = false)
                }
            }
        }
    }

    fun backToList() {
        navigator.navigateBack()
    }
}
