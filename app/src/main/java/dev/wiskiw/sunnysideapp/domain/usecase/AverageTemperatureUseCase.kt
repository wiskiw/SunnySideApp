package dev.wiskiw.sunnysideapp.domain.usecase

import dev.wiskiw.fakeforecastprovider.FakeForecastModule
import dev.wiskiw.openmeteoforecastprovider.di.OpenMeteoForecastModule
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.sunnysideapp.domain.model.AverageTemperature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AverageTemperatureUseCase @Inject constructor(
    @FakeForecastModule.Repository val fakeForecastRepository: ForecastRepository,
    @OpenMeteoForecastModule.Repository val openMeteoForecastRepository: ForecastRepository,
) {

    fun getTemperature(location: LatLng): Flow<AverageTemperature> = flow {
//        val fakeTemperatureResponse = fakeWeatherRepository.getTemperature(latLng)
        val temperatureResponse = openMeteoForecastRepository.getTemperature(location)

        when (temperatureResponse) {
            is Response.Success -> {
                val averageTemperature = AverageTemperature(
                    value = temperatureResponse.data,
                    sourceCount = 1,
                )
                emit(averageTemperature)
            }

            is Response.Failure -> error(temperatureResponse.error)
        }
    }
}
