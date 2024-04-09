package dev.wiskiw.sunnysideapp.domain.usecase

import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.sunnysideapp.data.service.location.LocationService
import dev.wiskiw.sunnysideapp.domain.model.AverageTemperature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalTemperatureUseCase @Inject constructor(
    private val locationService: LocationService,
    private val temperatureUseCase: AverageTemperatureUseCase,
) {

    companion object {
        private const val LOG_TAG = "LocalTempUC"
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTemperature(
        scope: CoroutineScope,
    ): Flow<AverageTemperature> = flow {
        val location = locationService.getLocation()
        val latLng = LatLng(location.latitude, location.longitude)
        emit(latLng)
    }.flatMapConcat { latLng ->
        temperatureUseCase.getTemperature(scope, latLng)
    }
}
