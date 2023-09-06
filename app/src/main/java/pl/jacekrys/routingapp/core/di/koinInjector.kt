package pl.jacekrys.routingapp.core.di

import org.koin.core.module.Module
import pl.jacekrys.routingapp.feature.route.di.routeModule

val koinInjector: List<Module> = listOf(routeModule)