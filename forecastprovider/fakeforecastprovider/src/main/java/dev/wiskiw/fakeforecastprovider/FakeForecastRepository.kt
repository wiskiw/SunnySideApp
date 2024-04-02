package dev.wiskiw.fakeforecastprovider

import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.shared.model.LatLng
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeForecastRepository @Inject constructor() : ForecastRepository {

    override suspend fun getTemperature(location: LatLng): Response<Float> {
        delay(1500)
        return Response.Success(11.2f)
    }
}
