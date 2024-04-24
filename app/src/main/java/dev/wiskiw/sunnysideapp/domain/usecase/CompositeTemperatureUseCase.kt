package dev.wiskiw.sunnysideapp.domain.usecase

import android.util.Log
import dev.wiskiw.fakeforecastprovider.FakeForecastModule
import dev.wiskiw.openmeteoforecastprovider.di.OpenMeteoForecastModule
import dev.wiskiw.openweathermap.di.OpenWeatherMapModule
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.shared.model.Response
import dev.wiskiw.sunnysideapp.domain.model.CompositeTemperature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompositeTemperatureUseCase @Inject constructor(
    val forecastRepositories : List<ForecastRepository>
//    @FakeForecastModule.Repository val fakeForecastRepository: ForecastRepository,
//    @OpenMeteoForecastModule.Repository val openMeteoForecastRepository: ForecastRepository,
//    @OpenWeatherMapModule.Repository val openWeatherMapRepository: ForecastRepository,
) {

    companion object {
        private const val LOG_TAG = "CompositeTempUC"
    }

    fun getTemperature(
        scope: CoroutineScope,
        latLng: LatLng,
    ): Flow<CompositeTemperature> {
        val temperatureResponseChannel = Channel<Response<Float>>()

        val temperatureFetchJobs = forecastRepositories.map { repository ->
            scope.launch(start = CoroutineStart.LAZY) {
                val temperatureResponse = repository.getTemperature(latLng)
                temperatureResponseChannel.send(temperatureResponse)
            }
        }

        val closeChannelHandler = { _: Throwable? ->
            val isAllJobsCompleted = temperatureFetchJobs.find { !it.isCompleted } == null
            if (isAllJobsCompleted) {
                temperatureResponseChannel.close()
            }
        }

        // Attach close channel handler and start temperature fetching jobs
        temperatureFetchJobs
            .onEach { it.invokeOnCompletion(closeChannelHandler) }
            .onEach { it.start() }

        return temperatureResponseChannel.averageTemperatureAsFlow()
    }

    private fun Channel<Response<Float>>.averageTemperatureAsFlow(): Flow<CompositeTemperature> =
        consumeAsFlow()
            .transform { temperatureResponse ->
                when (temperatureResponse) {
                    is Response.Success -> emit(temperatureResponse.data)
                    is Response.Failure -> Log.w(LOG_TAG, "Failed to fetch temperature", temperatureResponse.error)
                }
            }
            .runningFold(emptyList<Float>()) { acc, temperature -> acc + temperature }
            .filter { temperatures -> temperatures.isNotEmpty() }
            .map { temperatures ->
                CompositeTemperature(
                    value = temperatures.average().toFloat(),
                    sourceCount = temperatures.size,
                )
            }
}
