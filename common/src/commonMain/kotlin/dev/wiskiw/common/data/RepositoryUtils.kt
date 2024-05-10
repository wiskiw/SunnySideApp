package dev.wiskiw.common.data

import dev.wiskiw.common.data.model.DataLayerException
import dev.wiskiw.common.data.model.Response

suspend fun <T> wrapWithResponse(runBlock: suspend () -> T): Response<T> = try {
    val data = runBlock.invoke()
    Response.Success(data)
} catch (e: DataLayerException) {
    Response.Failure(e)
} catch (e: Exception) {
    val dataLayerException = DataLayerException(DataLayerException.Type.OTHER, e)
    Response.Failure(dataLayerException)
}

