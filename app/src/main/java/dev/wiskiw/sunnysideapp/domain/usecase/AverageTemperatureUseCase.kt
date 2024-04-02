package dev.wiskiw.sunnysideapp.domain.usecase

import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.sunnysideapp.data.model.Response
import dev.wiskiw.sunnysideapp.di.OpenMeteoWeatherModule
import dev.wiskiw.sunnysideapp.di.WeatherModule
import dev.wiskiw.sunnysideapp.domain.model.AverageTemperature
import dev.wiskiw.sunnysideapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AverageTemperatureUseCase @Inject constructor(
    @WeatherModule.FakeRepository val fakeWeatherRepository: WeatherRepository,
    @OpenMeteoWeatherModule.OpenMeteoRepository val openMeteoRepository: WeatherRepository,
) {

    fun getTemperature(location: LatLng): Flow<AverageTemperature> = flow {
//        val fakeTemperatureResponse = fakeWeatherRepository.getTemperature(latLng)
        val openMeteoTemperatureResponse = openMeteoRepository.getTemperature(location)

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
