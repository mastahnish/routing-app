package pl.jacekrys.routingapp.feature.route.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.jacekrys.routingapp.BuildConfig
import pl.jacekrys.routingapp.feature.route.data.RouteRepositoryImpl
import pl.jacekrys.routingapp.feature.route.data.remote.RouteApi
import pl.jacekrys.routingapp.feature.route.data.remote.RoutingApi
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository
import pl.jacekrys.routingapp.feature.route.domain.usecase.GetRouteDetailsUseCase
import pl.jacekrys.routingapp.feature.route.domain.usecase.GetRouteUseCase
import pl.jacekrys.routingapp.feature.route.domain.usecase.GetRoutesListUseCase
import pl.jacekrys.routingapp.feature.route.exception.RoutesErrorWrapper
import pl.jacekrys.routingapp.feature.route.exception.RoutingErrorWrapper
import pl.jacekrys.routingapp.feature.route.presentation.RoutesErrorMapper
import pl.jacekrys.routingapp.feature.route.presentation.details.RouteDetailsViewModel
import pl.jacekrys.routingapp.feature.route.presentation.list.RouteListViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val routeModule = module {
    // data
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BUSRIGHT_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()

        retrofit.create(RouteApi::class.java)
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.ROUTING_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()

        retrofit.create(RoutingApi::class.java)
    }

    single<RouteRepository> {
        RouteRepositoryImpl(get(), get(), get(), get())
    }

    factoryOf(::RoutesErrorWrapper)
    factoryOf(::RoutingErrorWrapper)

    // domain
    factoryOf(::GetRoutesListUseCase)
    factoryOf(::GetRouteUseCase)
    factoryOf(::GetRouteDetailsUseCase)

    // presentation
    factoryOf(::RoutesErrorMapper)
    viewModelOf(::RouteListViewModel)
    viewModelOf(::RouteDetailsViewModel)
}