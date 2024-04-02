package dev.wiskiw.fakeforecastprovider

import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.shared.model.Response
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class FakeForecastRepository @Inject constructor() : ForecastRepository {

    override suspend fun getTemperature(location: LatLng): Response<Float> {
        delay(3000)
        return Response.Success(Math.PI.toFloat())
    }
}
