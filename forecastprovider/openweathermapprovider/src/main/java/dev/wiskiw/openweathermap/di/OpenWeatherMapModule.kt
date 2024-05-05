package dev.wiskiw.openweathermap.di

import android.util.Log
import dev.wiskiw.openweathermap.data.OpenWeatherMapRepository
import dev.wiskiw.openweathermap.data.remote.OpenWeatherMapHttpService
import dev.wiskiw.openweathermap.data.remote.OpenWeatherMapService
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

private val namedOpenWeatherMapForecastHttpClient = named("openWeatherMapForecastHttpClient")
val namedOpenWeatherMapForecastRepository = named("openWeatherMapForecastRepository")

val openWeatherMapForecastModule = module {
    single<HttpClient>(namedOpenWeatherMapForecastHttpClient) {
        createHttpClient(get())
    }

    single<OpenWeatherMapService> {
        OpenWeatherMapHttpService(get(namedOpenWeatherMapForecastHttpClient))
    }

    single<ForecastRepository>(namedOpenWeatherMapForecastRepository) {
        OpenWeatherMapRepository(get())
    }
}

private fun createHttpClient(
    buildFieldsProvider: BuildFieldsProvider,
) = HttpClient(Android) {
    val ktorLogTag = "OWM Http"
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
            url("https://api.openweathermap.org/data/2.5/")
            protocol = URLProtocol.HTTPS
            parameters.append("appid", buildFieldsProvider.getApiKeys().openWeatherMapApiKey)
        }
    }
}
