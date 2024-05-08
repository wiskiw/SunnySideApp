package dev.wiskiw.shared.data.service

import dev.wiskiw.shared.data.model.LatLng

class AppleGeocoderService : GeocoderService {
    // todo implement GeocoderService for iOS
    override suspend fun getName(location: LatLng): String = "iOS Unknown Location Name"
}
