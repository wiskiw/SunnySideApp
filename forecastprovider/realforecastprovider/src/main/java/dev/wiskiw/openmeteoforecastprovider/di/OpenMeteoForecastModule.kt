package dev.wiskiw.openmeteoforecastprovider.di

import android.util.Log
import dev.wiskiw.openmeteoforecastprovider.data.OpenMeteoWeatherRepository
import dev.wiskiw.openmeteoforecastprovider.data.OpenWeatherMapRepository
import dev.wiskiw.openmeteoforecastprovider.data.remote.openmeteoweather.OpenMeteoHttpRemoteService
import dev.wiskiw.openmeteoforecastprovider.data.remote.openweathermap.OpenWeatherMapHttpRemoteService
import dev.wiskiw.shared.data.ForecastRepository
import dev.wiskiw.shared.utils.buildfields.BuildFieldsProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
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
import java.util.concurrent.TimeUnit

object Named {
    val OPEN_METEO_FORECAST_REPOSITORY = named("OPEN_METEO_FORECAST_REPOSITORY")
    val OPEN_WEATHER_MAP_FORECAST_REPOSITORY = named("OPEN_WEATHER_MAP_FORECAST_REPOSITORY")
}

private val httpJsonModule = module {
    single {
        HttpClient(Android) {
            val ktorLogTag = "KtorJsonHttp"
            val timeout = TimeUnit.SECONDS.toMillis(60).toInt()

            install(ContentNegotiation) {
                engine {
                    connectTimeout = timeout
                    socketTimeout = timeout
                }

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
                        Log.d(ktorLogTag, message)
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

    single<dev.wiskiw.shared.data.ForecastRepository>(Named.OPEN_METEO_FORECAST_REPOSITORY) {
        val service = OpenMeteoHttpRemoteService(get())
        return@single OpenMeteoWeatherRepository(
            openMeteoService = service,
        )
    }

    single<dev.wiskiw.shared.data.ForecastRepository>(Named.OPEN_WEATHER_MAP_FORECAST_REPOSITORY) {
        val apiKey = get<dev.wiskiw.shared.utils.buildfields.BuildFieldsProvider>().getApiKeys().openWeatherMapApiKey
        val service = OpenWeatherMapHttpRemoteService(get(), apiKey)
        return@single OpenWeatherMapRepository(
            weatherService = service,
        )
    }
}

