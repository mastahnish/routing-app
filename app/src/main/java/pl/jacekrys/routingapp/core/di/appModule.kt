package pl.jacekrys.routingapp.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.jacekrys.routingapp.core.navigation.Navigator

val appModule = module{
    singleOf(::Navigator)
}