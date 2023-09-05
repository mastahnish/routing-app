package pl.jacekrys.routingapp.feature.route.presentation.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RouteListViewModel : ViewModel() {
    private val _state by lazy { MutableStateFlow<RouteListState>(RouteListState()) }
    val state = _state.asStateFlow()
}