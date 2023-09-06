package pl.jacekrys.routingapp.feature.route.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import pl.jacekrys.routingapp.feature.route.presentation.details.RouteDetailsViewModel
import pl.jacekrys.routingapp.feature.route.presentation.list.RouteListViewModel

val routeModule = module {
    // data

    // domain

    // presentation
    viewModelOf(::RouteListViewModel)
    viewModelOf(::RouteDetailsViewModel)
}