package dev.wiskiw.sunnysideapp.domain.usecase

import dev.wiskiw.fakeforecastprovider.FakeForecastModule
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.sunnysideapp.di.OpenMeteoWeatherModule
import dev.wiskiw.sunnysideapp.domain.model.AverageTemperature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AverageTemperatureUseCase @Inject constructor(
    @FakeForecastModule.FakeRepository val fakeForecastRepository: ForecastRepository,
    @OpenMeteoWeatherModule.OpenMeteoRepository val openForecastRepository: ForecastRepository,
) {

    fun getTemperature(location: LatLng): Flow<AverageTemperature> = flow {
//        val fakeTemperatureResponse = fakeWeatherRepository.getTemperature(latLng)
        val openMeteoTemperatureResponse = fakeForecastRepository.getTemperature(location)

        when (openMeteoTemperatureResponse) {
            is Response.Success -> {
                val averageTemperature = AverageTemperature(
                    value = openMeteoTemperatureResponse.data,
                    sourceCount = 1,
                )
                emit(averageTemperature)
            }

            is Response.Failure -> error(openMeteoTemperatureResponse.error)
        }
    }
}
