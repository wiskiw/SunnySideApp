package dev.wiskiw.shared.utils

import dev.wiskiw.shared.model.DataLayerException
import dev.wiskiw.shared.model.Response

suspend fun <T> wrapWithResponse(runBlock: suspend () -> T): Response<T> = try {
    val data = runBlock.invoke()
    Response.Success(data)
} catch (e: DataLayerException) {
    Response.Failure(e)
} catch (e: Exception) {
    val dataLayerException = DataLayerException(DataLayerException.Type.OTHER, e)
    Response.Failure(dataLayerException)
}

