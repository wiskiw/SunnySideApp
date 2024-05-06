package dev.wiskiw.sunnysideapp.usecase

import app.cash.turbine.test
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.model.LatLng
import dev.wiskiw.shared.model.Response
import dev.wiskiw.sunnysideapp.domain.usecase.CompositeTemperatureUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CompositeTemperatureUseCaseText {

    companion object {
        private fun createFakeForecastRepository(temperatureResponse: Response<Float>) = object : ForecastRepository {
            override suspend fun getTemperature(latLng: LatLng): Response<Float> = temperatureResponse
        }
    }

    @Test
    fun `composite temperature is equal to forecast repository temperature if only one forecast repository provided`() {
        val mockedTemperatureValue = 111f
        val forecastRepositories = listOf(mockedTemperatureValue)
            .map { temperatureValue -> createFakeForecastRepository(Response.Success(temperatureValue)) }
        val useCase = CompositeTemperatureUseCase(forecastRepositories = forecastRepositories)
        val mockedLatLng = LatLng(latitude = 52.025749274404596, longitude = 11.58012192571578)

        runTest {
            useCase.getTemperature(scope = this, latLng = mockedLatLng)
                .test {
                    assertEquals(mockedTemperatureValue, awaitItem().value)
                    awaitComplete()
                }
        }
    }

    @Test
    fun `composite temperature is equal to average temperature of all forecast repositories`() {
        val mockedDataValues = listOf(7f, 111f, 13f)
        val forecastRepositories = mockedDataValues
            .map { temperatureValue -> createFakeForecastRepository(Response.Success(temperatureValue)) }
        val useCase = CompositeTemperatureUseCase(forecastRepositories = forecastRepositories)
        val mockedLatLng = LatLng(latitude = 52.025749274404596, longitude = 11.58012192571578)

        runTest {
            useCase.getTemperature(scope = this, latLng = mockedLatLng)
                .test {
                    skipItems(mockedDataValues.size - 1)
                    assertEquals(mockedDataValues.average().toFloat(), awaitItem().value)
                    awaitComplete()
                }
        }
    }

    @Test
    fun `composite temperature is equal to single temperature, when all temperatures are the same`() {
        val mockedDataValue = 10f
        val forecastRepositories = listOf(
            createFakeForecastRepository(Response.Success(mockedDataValue)),
            createFakeForecastRepository(Response.Success(mockedDataValue)),
            createFakeForecastRepository(Response.Success(mockedDataValue)),
        )
        val useCase = CompositeTemperatureUseCase(forecastRepositories = forecastRepositories)
        val mockedLatLng = LatLng(latitude = 52.025749274404596, longitude = 11.58012192571578)

        runTest {
            useCase.getTemperature(scope = this, latLng = mockedLatLng)
                .test {
                    assertEquals(mockedDataValue, awaitItem().value)
                    assertEquals(mockedDataValue, awaitItem().value)
                    assertEquals(mockedDataValue, awaitItem().value)
                    awaitComplete()
                }
        }
    }
}