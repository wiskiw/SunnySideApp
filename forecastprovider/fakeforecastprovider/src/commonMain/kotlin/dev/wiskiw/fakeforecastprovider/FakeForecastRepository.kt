package dev.wiskiw.fakeforecastprovider

import dev.wiskiw.common.data.ForecastRepository
import dev.wiskiw.common.data.model.DataLayerException
import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.common.data.model.Response
import dev.wiskiw.common.data.wrapWithResponse
import kotlinx.coroutines.delay

internal class FakeForecastRepository : ForecastRepository {

    override suspend fun getTemperature(latLng: LatLng): Response<Float> =
        wrapWithResponse {
            delay(3000)
            throw DataLayerException(DataLayerException.Type.SERVER_ERROR)
        }
}
