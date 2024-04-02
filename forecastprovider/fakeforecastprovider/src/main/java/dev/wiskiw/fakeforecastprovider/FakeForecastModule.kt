package dev.wiskiw.fakeforecastprovider

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wiskiw.shared.data.ForecastRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FakeForecastModule {

    @Qualifier
    annotation class Repository

    @Repository
    @Binds
    @Singleton
    internal abstract fun bindFakeWeatherRepository(impl: FakeForecastRepository): ForecastRepository
}