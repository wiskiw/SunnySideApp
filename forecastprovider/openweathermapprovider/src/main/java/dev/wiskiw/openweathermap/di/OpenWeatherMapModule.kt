package dev.wiskiw.openweathermap.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wiskiw.openweathermap.data.OpenWeatherMapRepository
import dev.wiskiw.openweathermap.data.remote.OpenWeatherMapHttpService
import dev.wiskiw.openweathermap.data.remote.OpenWeatherMapService
import dev.wiskiw.shared.data.ForecastRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OpenWeatherMapModule {

    @Qualifier
    annotation class Repository

    @Qualifier
    annotation class OpenWetaherMapHttpClient

    @Repository
    @Binds
    @Singleton
    internal abstract fun bindOpenWeatherMapRepository(impl: OpenWeatherMapRepository): ForecastRepository

    @Binds
    @Singleton
    internal abstract fun bindOpenWeatherMapService(impl: OpenWeatherMapHttpService): OpenWeatherMapService
}