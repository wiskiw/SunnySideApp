package dev.wiskiw.shared.domain.model

import dev.wiskiw.common.data.model.LatLng

data class Address(
    val latLng: LatLng,
    val name: String?,
)
