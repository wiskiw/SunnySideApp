package dev.wiskiw.shared.domain.model

data class CompositeTemperature(
    val value: Float,
    val availableSources : List<String>,
    val unavailableSources : List<String>,
)
