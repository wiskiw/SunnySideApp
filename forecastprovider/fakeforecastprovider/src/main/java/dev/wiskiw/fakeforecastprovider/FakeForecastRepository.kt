package dev.wiskiw.fakeforecastprovider

import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.data.model.DataLayerException
import dev.wiskiw.shared.domain.model.LatLng
import dev.wiskiw.shared.data.model.Response
import dev.wiskiw.shared.data.wrapWithResponse
import kotlinx.coroutines.delay

internal class FakeForecastRepository : ForecastRepository {

    override suspend fun getTemperature(latLng: dev.wiskiw.shared.domain.model.LatLng): dev.wiskiw.shared.data.model.Response<Float> =
        wrapWithResponse {
            delay(3000)
            throw dev.wiskiw.shared.data.model.DataLayerException(dev.wiskiw.shared.data.model.DataLayerException.Type.SERVER_ERROR)
        }
}
