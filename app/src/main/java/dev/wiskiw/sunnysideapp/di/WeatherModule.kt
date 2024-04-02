package dev.wiskiw.sunnysideapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wiskiw.sunnysideapp.data.FakeWeatherRepository
import dev.wiskiw.sunnysideapp.domain.repository.WeatherRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface WeatherModule {

    @Qualifier
    annotation class FakeRepository

    @FakeRepository
    @Binds
    @Singleton
    fun bindFakeWeatherRepository(implementation: FakeWeatherRepository): WeatherRepository
}