package dev.wiskiw.sunnysideapp.app

import dev.wiskiw.fakeforecastprovider.fakeForecastModule
import dev.wiskiw.fakeforecastprovider.namedFakeForecastRepository
import dev.wiskiw.openmeteoforecastprovider.di.namedOpenMateoForecastRepository
import dev.wiskiw.openmeteoforecastprovider.di.openMeteoForecastModule
import dev.wiskiw.openweathermap.di.namedOpenWeatherMapForecastRepository
import dev.wiskiw.openweathermap.di.openWeatherMapForecastModule
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.utils.buildfields.BuildFieldsProvider
import dev.wiskiw.sunnysideapp.data.service.location.FusedLocationService
import dev.wiskiw.sunnysideapp.data.service.location.LocationService
import dev.wiskiw.sunnysideapp.domain.usecase.CompositeTemperatureUseCase
import dev.wiskiw.sunnysideapp.domain.usecase.LocalTemperatureUseCase
import dev.wiskiw.sunnysideapp.domain.usecase.LocationAddressUseCase
import dev.wiskiw.sunnysideapp.presentation.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sunnySideModule = module {

    single<BuildFieldsProvider> { SunnySideAppBuildFieldsProvider() }

    includes(
        fakeForecastModule,
        openMeteoForecastModule,
        openWeatherMapForecastModule,
    )

    single {
        val forecastRepositories = listOf<ForecastRepository>(
            get(namedFakeForecastRepository),
            get(namedOpenMateoForecastRepository),
            get(namedOpenWeatherMapForecastRepository),
        )
        CompositeTemperatureUseCase(forecastRepositories)
    }

    single<LocationService> { FusedLocationService(get()) }
    single { LocationAddressUseCase(get()) }
    single { LocalTemperatureUseCase(get(), get(), get()) }

    viewModel { HomeViewModel(get()) }
//    includes(viewModelModule)
}

private val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}
