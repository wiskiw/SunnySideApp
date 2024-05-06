package dev.wiskiw.shared.model

class DataLayerException(
    val type: Type,
    exception: Exception? = null,
) : RuntimeException("Exception with type '${type.name}'", exception) {

    enum class Type {
        CONNECTION_TIMEOUT,
        SERVER_ERROR,
        OTHER,
    }

}
