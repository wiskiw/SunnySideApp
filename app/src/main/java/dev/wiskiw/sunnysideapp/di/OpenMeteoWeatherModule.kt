package dev.wiskiw.sunnysideapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wiskiw.sunnysideapp.data.OpenMeteoWeatherRepository
import dev.wiskiw.sunnysideapp.data.remote.OpenMeteoService
import dev.wiskiw.sunnysideapp.domain.repository.WeatherRepository
import io.ktor.client.HttpClient
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OpenMeteoWeatherModule {

    @Qualifier
    annotation class OpenMeteoRepository

    @OpenMeteoRepository
    @Provides
    @Singleton
    fun provideOpenMeteoRepository(jsonClient: HttpClient): WeatherRepository {
        return OpenMeteoWeatherRepository(
            openMeteoService = OpenMeteoService(jsonClient),
        )
    }
}