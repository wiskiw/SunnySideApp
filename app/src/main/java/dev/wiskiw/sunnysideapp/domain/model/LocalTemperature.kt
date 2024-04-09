package dev.wiskiw.sunnysideapp.domain.model

import android.location.Address

data class LocalTemperature(
    val temperature: AverageTemperature,
    val address: Address?,
)
