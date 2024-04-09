package dev.wiskiw.sunnysideapp.domain.usecase

import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.sunnysideapp.data.service.location.LocationService
import dev.wiskiw.sunnysideapp.domain.model.LocalTemperature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class LocalTemperatureUseCase @Inject constructor(
    private val locationService: LocationService,
    private val locationAddressUseCase: LocationAddressUseCase,
    private val temperatureUseCase: AverageTemperatureUseCase,
) {

    companion object {
        private const val LOG_TAG = "LocalTempUC"
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTemperature(
        scope: CoroutineScope,
    ): Flow<LocalTemperature> = flow {
        val location = locationService.getLocation()
        emit(location)
    }.flatMapConcat { location ->
        val address = locationAddressUseCase.getAddress(location)
        val latLng = LatLng(location.latitude, location.longitude)

        temperatureUseCase.getTemperature(scope, latLng)
            .transform {
                val localTemp = LocalTemperature(
                    temperature = it,
                    address = address,
                )
                emit(localTemp)
            }
    }
}
