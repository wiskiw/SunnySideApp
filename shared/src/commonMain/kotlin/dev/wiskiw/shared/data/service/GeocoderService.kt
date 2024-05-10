package dev.wiskiw.shared.data.service

import dev.wiskiw.common.data.model.LatLng


interface GeocoderService {
    suspend fun getName(location: LatLng): String?
}
