package dev.wiskiw.sunnysideapp.data

import dev.wiskiw.sunnysideapp.data.model.Response
import dev.wiskiw.sunnysideapp.domain.repository.WeatherRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeWeatherRepository @Inject constructor() : WeatherRepository {

    override suspend fun getTemperature(): Response<Float> {
        delay(1500)
        return Response.Success(11.2f)
    }
}
