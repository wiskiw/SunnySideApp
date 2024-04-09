package dev.wiskiw.sunnysideapp.app

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wiskiw.sunnysideapp.data.service.location.FusedLocationService
import dev.wiskiw.sunnysideapp.data.service.location.LocationService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SunnySideModule {

    @Provides
    @Singleton
    fun providesDeviceLocationService(
        application: Application,
    ): LocationService = FusedLocationService(application)

}
