package dev.wiskiw.common.data.model

class DataLayerException : RuntimeException {

    val type: Type

    constructor(type: Type, exception: Exception? = null) : super("Exception with type '${type.name}'", exception) {
        this.type = type
    }

    constructor(type: Type, message: String?) : super(message) {
        this.type = type
    }


    enum class Type {
        CONNECTION_TIMEOUT,
        SERVER_ERROR,
        OTHER,
    }
}
