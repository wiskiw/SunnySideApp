package dev.wiskiw.fakeforecastprovider

import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.model.DataLayerException
import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.shared.model.Response
import dev.wiskiw.shared.utils.wrapWithResponse
import kotlinx.coroutines.delay

internal class FakeForecastRepository : ForecastRepository {

    override suspend fun getTemperature(latLng: LatLng): Response<Float> = wrapWithResponse {
        delay(3000)
        throw DataLayerException(DataLayerException.Type.SERVER_ERROR)
    }
}
