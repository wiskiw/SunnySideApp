package dev.wiskiw.shared.data.service

import dev.wiskiw.shared.data.model.LatLng


class FusedLocationService : LocationService {

    override suspend fun getLocation(): LatLng {
        // TODO implement location service for ios
        return LatLng(0.0, 0.0)
    }
}
