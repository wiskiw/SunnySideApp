package dev.wiskiw.shared.domain.usecase

import dev.wiskiw.shared.data.service.GeocoderService
import dev.wiskiw.shared.data.service.LocationService
import dev.wiskiw.shared.domain.model.Address
import dev.wiskiw.shared.domain.model.LocalTemperature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform

class LocalTemperatureUseCase(
    private val locationService: LocationService,
    private val geocoderService: GeocoderService,
    private val temperatureUseCase: CompositeTemperatureUseCase,
) {

    companion object {
        private const val LOG_TAG = "LocalTempUC"
    }

    fun getLocalTemperature(
        scope: CoroutineScope,
    ): Flow<LocalTemperature> = flow {
        val location = locationService.getLocation()
        val address = Address(
            latLng = location,
            name = geocoderService.getName(location),
        )
        val temperatureFlow = temperatureUseCase.getTemperature(scope, location)
            .transform {
                val localTemp = LocalTemperature(
                    temperature = it,
                    address = address,
                )
                emit(localTemp)
            }

        emitAll(temperatureFlow)
    }
}
