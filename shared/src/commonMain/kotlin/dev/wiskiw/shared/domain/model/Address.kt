package dev.wiskiw.shared.domain.model

import dev.wiskiw.shared.data.model.LatLng

data class Address(
    val latLng: LatLng,
    val name: String?,
)
