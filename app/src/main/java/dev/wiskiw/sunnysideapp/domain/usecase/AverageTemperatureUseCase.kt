package dev.wiskiw.sunnysideapp.domain.usecase

import dev.wiskiw.sunnysideapp.data.model.Response
import dev.wiskiw.sunnysideapp.di.WeatherModule
import dev.wiskiw.sunnysideapp.domain.model.AverageTemperature
import dev.wiskiw.sunnysideapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AverageTemperatureUseCase @Inject constructor(
    @WeatherModule.FakeRepository val fakeWeatherRepository: WeatherRepository,
) {

    fun getTemperature(): Flow<AverageTemperature> = flow {
        val fakeTemperatureResponse = fakeWeatherRepository.getTemperature()

        when (fakeTemperatureResponse) {
            is Response.Success -> {
                val averageTemperature = AverageTemperature(
                    value = fakeTemperatureResponse.data,
                    sourceCount = 1,
                )
                emit(averageTemperature)
            }

            is Response.Failure -> error(fakeTemperatureResponse.error)
        }
    }
}
