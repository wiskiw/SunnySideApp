package dev.wiskiw.sunnysideapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wiskiw.sunnysideapp.data.FakeWeatherRepository
import dev.wiskiw.sunnysideapp.domain.repository.WeatherRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Qualifier
    annotation class FakeRepository

    @FakeRepository
    @Provides
    @Singleton
    fun provideFakeWeatherRepository(): WeatherRepository = FakeWeatherRepository()
}