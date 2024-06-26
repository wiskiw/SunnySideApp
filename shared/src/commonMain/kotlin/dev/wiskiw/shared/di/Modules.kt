package dev.wiskiw.shared.di

import dev.wiskiw.common.data.ForecastRepository
import dev.wiskiw.common.utils.buildfields.BuildFieldsProvider
import dev.wiskiw.common.utils.buildfields.SunnySideAppBuildFieldsProvider
import dev.wiskiw.fakeforecastprovider.fakeForecastModule
import dev.wiskiw.realforecastprovider.realForecastModule
import dev.wiskiw.shared.data.service.GeocoderService
import dev.wiskiw.shared.data.service.LocationService
import dev.wiskiw.shared.data.service.createFusedLocationService
import dev.wiskiw.shared.data.service.createNativeGeocoderService
import dev.wiskiw.shared.domain.usecase.CompositeTemperatureUseCase
import dev.wiskiw.shared.domain.usecase.LocalTemperatureUseCase
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.dsl.module
import dev.wiskiw.fakeforecastprovider.Named as FakeModuleNamed
import dev.wiskiw.realforecastprovider.Named as RealModuleNamed

private val platformModule = module {
    single<LocationService> { createFusedLocationService() }
    single<GeocoderService> { createNativeGeocoderService() }
}

private val commonModule = module {
    Napier.base(DebugAntilog())

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
            get(RealModuleNamed.WEATHER_BIT_FORECAST_REPOSITORY),
            get(RealModuleNamed.METEO_SOURCE_FORECAST_REPOSITORY),
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
}

val appModules = listOf(platformModule, commonModule)
