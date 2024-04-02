package dev.wiskiw.sunnysideapp.data

import dev.wiskiw.sunnysideapp.common.model.LatLng
import dev.wiskiw.sunnysideapp.data.model.Response
import dev.wiskiw.sunnysideapp.domain.repository.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeWeatherRepository @Inject constructor(
    val client : HttpClient,
) : WeatherRepository {

    override suspend fun getTemperature(location: LatLng): Response<Float> {
        delay(1500)

        client.request("https://ktor.io/")

        return Response.Success(11.2f)
    }
}
