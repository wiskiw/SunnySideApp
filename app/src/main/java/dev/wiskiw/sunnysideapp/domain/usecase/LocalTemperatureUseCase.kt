package dev.wiskiw.sunnysideapp.domain.usecase

import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.sunnysideapp.data.service.location.LocationService
import dev.wiskiw.sunnysideapp.domain.model.LocalTemperature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class LocalTemperatureUseCase @Inject constructor(
    private val locationService: LocationService,
    private val locationAddressUseCase: LocationAddressUseCase,
    private val temperatureUseCase: CompositeTemperatureUseCase,
) {

    companion object {
        private const val LOG_TAG = "LocalTempUC"
    }

    fun getLocalTemperature(
        scope: CoroutineScope,
    ): Flow<LocalTemperature> = flow {
        val location = locationService.getLocation()
        val address = locationAddressUseCase.getAddress(location)
        val latLng = LatLng(location.latitude, location.longitude)

        val temperatureFlow = temperatureUseCase.getTemperature(scope, latLng)
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
