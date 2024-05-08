package dev.wiskiw.shared.domain.model

data class LocalTemperature(
    val temperature: CompositeTemperature,
    val address: Address,
)
