package dev.wiskiw.realforecastprovider

import dev.wiskiw.common.data.ForecastRepository
import dev.wiskiw.common.utils.buildfields.BuildFieldsProvider
import dev.wiskiw.realforecastprovider.data.OpenMeteoWeatherRepository
import dev.wiskiw.realforecastprovider.data.OpenWeatherMapRepository
import dev.wiskiw.realforecastprovider.data.remote.openmeteoweather.OpenMeteoHttpRemoteService
import dev.wiskiw.realforecastprovider.data.remote.openweathermap.OpenWeatherMapHttpRemoteService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

object Named {
    val OPEN_METEO_FORECAST_REPOSITORY = named("OPEN_METEO_FORECAST_REPOSITORY")
    val OPEN_WEATHER_MAP_FORECAST_REPOSITORY = named("OPEN_WEATHER_MAP_FORECAST_REPOSITORY")
}

private val httpJsonModule = module {
    single {
        HttpClient {

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        // todo update logs
                        println(message)
                    }
                }
            }

            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }
}

val realForecastModule = module {
    includes(httpJsonModule)

    single<ForecastRepository>(Named.OPEN_METEO_FORECAST_REPOSITORY) {
        val service = OpenMeteoHttpRemoteService(get())
        return@single OpenMeteoWeatherRepository(
            openMeteoService = service,
        )
    }

    single<ForecastRepository>(Named.OPEN_WEATHER_MAP_FORECAST_REPOSITORY) {
        val apiKey = get<BuildFieldsProvider>().getApiKeys().openWeatherMapApiKey
        val service = OpenWeatherMapHttpRemoteService(get(), apiKey)
        return@single OpenWeatherMapRepository(
            weatherService = service,
        )
    }
}
