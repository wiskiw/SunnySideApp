package dev.wiskiw.shared.data.service

import dev.wiskiw.shared.data.model.LatLng
import org.koin.core.scope.Scope

interface LocationService {
    suspend fun getLocation(): LatLng
}
