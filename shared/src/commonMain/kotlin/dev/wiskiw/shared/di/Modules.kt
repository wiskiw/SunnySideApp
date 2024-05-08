package dev.wiskiw.shared.di

import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.data.model.LatLng
import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.shared.data.service.GeocoderService
import dev.wiskiw.shared.data.service.LocationService
import dev.wiskiw.shared.data.service.createFusedLocationService
import dev.wiskiw.shared.data.service.createNativeGeocoderService
import dev.wiskiw.shared.data.wrapWithResponse
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

//    includes(
//        fakeForecastModule,
//        realForecastModule,
//    )

    single {
        val fakeFakeForecastRepository = object : ForecastRepository {
            override suspend fun getTemperature(latLng: LatLng): Response<Float> = wrapWithResponse {
                delay(3000)
                130.3f
            }
        }

        val forecastRepositories = listOf<ForecastRepository>(
            fakeFakeForecastRepository,
//            get(FakeModuleNamed.FAKE_FORECAST_REPOSITORY),
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
