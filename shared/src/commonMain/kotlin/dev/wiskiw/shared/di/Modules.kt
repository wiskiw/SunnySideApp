package dev.wiskiw.shared.di

import dev.wiskiw.common.data.ForecastRepository
import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.common.data.model.Response
import dev.wiskiw.shared.data.service.GeocoderService
import dev.wiskiw.shared.data.service.LocationService
import dev.wiskiw.shared.data.service.createFusedLocationService
import dev.wiskiw.shared.data.service.createNativeGeocoderService
import dev.wiskiw.common.wrapWithResponse
import dev.wiskiw.fakeforecastprovider.Named as FakeModuleNamed
import dev.wiskiw.fakeforecastprovider.fakeForecastModule
import dev.wiskiw.shared.domain.usecase.CompositeTemperatureUseCase
import dev.wiskiw.shared.domain.usecase.LocalTemperatureUseCase
import dev.wiskiw.shared.utils.buildfields.BuildFieldsProvider
import dev.wiskiw.shared.utils.buildfields.SunnySideAppBuildFieldsProvider
import kotlinx.coroutines.delay
import org.koin.dsl.module

private val platformModule = module {
    single<LocationService> { createFusedLocationService() }
    single<GeocoderService> { createNativeGeocoderService() }
}

private val commonModule = module {
    single<BuildFieldsProvider> { SunnySideAppBuildFieldsProvider() }

    includes(
        fakeForecastModule,
//        realForecastModule,
    )

    single {
        val forecastRepositories = listOf<ForecastRepository>(
            get(FakeModuleNamed.FAKE_FORECAST_REPOSITORY),
//            get(RealModuleNamed.OPEN_METEO_FORECAST_REPOSITORY),
//            get(RealModuleNamed.OPEN_WEATHER_MAP_FORECAST_REPOSITORY),

        )
        CompositeTemperatureUseCase(forecastRepositories)
    }

    single {
        LocalTemperatureUseCase(
            locationService = get(),
            geocoderService = get(),
            temperatureUseCase = get(),
        )
    }

//    includes(viewModelModule)
}

val appModules = listOf(platformModule, commonModule)
