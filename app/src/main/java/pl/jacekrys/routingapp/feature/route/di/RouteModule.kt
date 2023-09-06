package pl.jacekrys.routingapp.feature.route.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.jacekrys.routingapp.BuildConfig
import pl.jacekrys.routingapp.feature.route.data.RouteRepositoryImpl
import pl.jacekrys.routingapp.feature.route.data.remote.RouteApi
import pl.jacekrys.routingapp.feature.route.domain.repository.RouteRepository
import pl.jacekrys.routingapp.feature.route.presentation.details.RouteDetailsViewModel
import pl.jacekrys.routingapp.feature.route.presentation.list.RouteListViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val routeModule = module {
    // data
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
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

    single<RouteRepository> {
        RouteRepositoryImpl(get())
    }

    // domain

    // presentation
    viewModelOf(::RouteListViewModel)
    viewModelOf(::RouteDetailsViewModel)
}