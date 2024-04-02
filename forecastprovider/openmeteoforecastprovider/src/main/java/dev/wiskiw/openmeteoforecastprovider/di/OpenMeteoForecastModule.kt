package dev.wiskiw.openmeteoforecastprovider.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wiskiw.openmeteoforecastprovider.data.OpenMeteoWeatherRepository
import dev.wiskiw.openmeteoforecastprovider.data.remote.OpenMeteoHttpRemoteService
import dev.wiskiw.openmeteoforecastprovider.data.remote.OpenMeteoRemoteService
import dev.wiskiw.shared.data.ForecastRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OpenMeteoForecastModule {

    @Qualifier
    annotation class Repository

    @Repository
    @Binds
    @Singleton
    internal abstract fun bindOpenMeteoRepository(impl: OpenMeteoWeatherRepository): ForecastRepository

    @Binds
    @Singleton
    internal abstract fun bindOpenMeteoRemoteService(impl: OpenMeteoHttpRemoteService): OpenMeteoRemoteService
}