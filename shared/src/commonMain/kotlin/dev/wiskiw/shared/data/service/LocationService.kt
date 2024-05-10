package dev.wiskiw.shared.data.service

import dev.wiskiw.common.data.model.LatLng

interface LocationService {
    suspend fun getLocation(): LatLng
}
