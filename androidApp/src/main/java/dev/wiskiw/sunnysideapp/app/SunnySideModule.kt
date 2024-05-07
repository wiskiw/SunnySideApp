package dev.wiskiw.sunnysideapp.app

import dev.wiskiw.fakeforecastprovider.fakeForecastModule
import dev.wiskiw.openmeteoforecastprovider.di.realForecastModule
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
import dev.wiskiw.fakeforecastprovider.Named as FakeModuleNamed
import dev.wiskiw.openmeteoforecastprovider.di.Named as RealModuleNamed

// Should be declared above usage in includes(..)
// bug: https://github.com/InsertKoinIO/koin/issues/1341
private val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}

val sunnySideModule = module {

    single<BuildFieldsProvider> { SunnySideAppBuildFieldsProvider() }

    includes(
        fakeForecastModule,
        realForecastModule,
    )

    single {
        val forecastRepositories = listOf<ForecastRepository>(
            get(FakeModuleNamed.FAKE_FORECAST_REPOSITORY),
            get(RealModuleNamed.OPEN_METEO_FORECAST_REPOSITORY),
            get(RealModuleNamed.OPEN_WEATHER_MAP_FORECAST_REPOSITORY),
        )
        CompositeTemperatureUseCase(forecastRepositories)
    }

    single<LocationService> { FusedLocationService(get()) }
    single { LocationAddressUseCase(get()) }
    single { LocalTemperatureUseCase(get(), get(), get()) }

    includes(viewModelModule)
}
