package dev.wiskiw.sunnysideapp.app

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wiskiw.fakeforecastprovider.FakeForecastModule
import dev.wiskiw.openmeteoforecastprovider.di.OpenMeteoForecastModule
import dev.wiskiw.openweathermap.di.OpenWeatherMapModule
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.utils.buildfields.BuildFieldsProvider
import dev.wiskiw.sunnysideapp.data.service.location.FusedLocationService
import dev.wiskiw.sunnysideapp.data.service.location.LocationService
import dev.wiskiw.sunnysideapp.domain.usecase.CompositeTemperatureUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SunnySideModule {

    @Provides
    @Singleton
    fun providesFusedLocationService(
        application: Application,
    ): LocationService = FusedLocationService(application)

    @Provides
    @Singleton
    internal fun provideBuildConfigFieldsProvider(): BuildFieldsProvider = SunnySideAppBuildFieldsProvider()

    @Provides
    @Singleton
    internal fun provideCompositeTemperatureUseCase(
        @OpenMeteoForecastModule.Repository openMeteoForecastRepository: ForecastRepository,
        @OpenWeatherMapModule.Repository openWeatherMapRepository: ForecastRepository,
        @FakeForecastModule.Repository fakeRepository: ForecastRepository,
    ): CompositeTemperatureUseCase {
        val forecastRepositories = listOf(
            openMeteoForecastRepository,
            openWeatherMapRepository,
            fakeRepository,
        )
        return CompositeTemperatureUseCase(forecastRepositories)
    }

}
