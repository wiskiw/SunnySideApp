package dev.wiskiw.shared.domain.usecase

import dev.wiskiw.common.data.ForecastRepository
import dev.wiskiw.common.data.model.LatLng
import dev.wiskiw.common.data.model.Response
import dev.wiskiw.shared.domain.model.CompositeTemperature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.launch

class CompositeTemperatureUseCase(
    val forecastRepositories: List<ForecastRepository>,
) {

    companion object {
        private const val LOG_TAG = "CompositeTempUC"
    }

    private data class TemperatureData(
        val value: Float,
        val sourceName: String,
    )

    fun getTemperature(
        scope: CoroutineScope,
        latLng: LatLng,
    ): Flow<CompositeTemperature> {
        val temperatureDataChannel = Channel<TemperatureData>()

        val temperatureFetchJobs = forecastRepositories.map { repository ->
            scope.launch(start = CoroutineStart.LAZY) {
                val temperatureData = fetchTemperatureData(latLng = latLng, repository = repository)
                temperatureDataChannel.send(temperatureData)
            }
        }

        val closeChannelHandler = { _: Throwable? ->
            val isAllJobsCompleted = temperatureFetchJobs.find { !it.isCompleted } == null
            if (isAllJobsCompleted) {
                temperatureDataChannel.close()
            }
        }

        // Attach close channel handler and start temperature fetching jobs
        temperatureFetchJobs
            .onEach { it.invokeOnCompletion(closeChannelHandler) }
            .onEach { it.start() }

        return temperatureDataChannel.averageTemperatureAsFlow()
    }

    private suspend fun fetchTemperatureData(
        latLng: LatLng,
        repository: ForecastRepository,
    ): TemperatureData {
        when (
            val temperatureResponse = repository.getTemperature(latLng)
        ) {
            is Response.Success -> {
                // todo fix sourceName
                return TemperatureData(
                    value = temperatureResponse.data,
                    sourceName = "unknown",
                )
            }

            is Response.Failure -> {
                // todo fix sourceName
                // todo fix logger
//                Log.w(LOG_TAG, "Failed to fetch temperature", temperatureResponse.error)
                return TemperatureData(
                    value = Float.NaN,
                    sourceName = "unknown",
                )
            }
        }
    }

    private fun Channel<TemperatureData>.averageTemperatureAsFlow(): Flow<CompositeTemperature> =
        consumeAsFlow()
            .runningFold(emptyList<TemperatureData>()) { acc, temperature -> acc + temperature }
            .filter { temperatures -> temperatures.isNotEmpty() }
            .map { temperatures ->
                CompositeTemperature(
                    value = temperatures.filter { !it.value.isNaN() }.map { it.value }.average().toFloat(),
                    availableSources = temperatures.filter { !it.value.isNaN() }.map { it.sourceName },
                    unavailableSources = temperatures.filter { it.value.isNaN() }.map { it.sourceName },
                )
            }
}
