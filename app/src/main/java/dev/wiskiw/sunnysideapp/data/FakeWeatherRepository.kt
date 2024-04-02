package dev.wiskiw.sunnysideapp.data

import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.shared.data.ForecastRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeWeatherRepository @Inject constructor(
    val client : HttpClient,
) : ForecastRepository {

    override suspend fun getTemperature(location: LatLng): Response<Float> {
        delay(1500)

        client.request("https://ktor.io/")

        return Response.Success(11.2f)
    }
}
