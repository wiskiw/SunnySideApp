package dev.wiskiw.openmeteoforecastprovider.di

import android.util.Log
import dev.wiskiw.openmeteoforecastprovider.data.OpenMeteoWeatherRepository
import dev.wiskiw.openmeteoforecastprovider.data.remote.OpenMeteoHttpRemoteService
import dev.wiskiw.openmeteoforecastprovider.data.remote.OpenMeteoRemoteService
import dev.wiskiw.shared.data.ForecastRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

private val namedOpenMateoForecastHttpClient = named("namedOpenMateoForecastHttpClient")
val namedOpenMateoForecastRepository = named("namedOpenMateoForecastRepository")

val openMeteoForecastModule = module {
    single(namedOpenMateoForecastHttpClient) {
        createHttpClient()
    }

    single<OpenMeteoRemoteService> {
        OpenMeteoHttpRemoteService(
            get(namedOpenMateoForecastHttpClient),
        )
    }

    single<ForecastRepository>(namedOpenMateoForecastRepository) {
        OpenMeteoWeatherRepository(get())
    }
}

private fun createHttpClient() = HttpClient(Android) {
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
        logger = Logger.SIMPLE
    }

    install(ResponseObserver) {
        onResponse { response ->
            Log.d("HTTP status:", "${response.status.value}")
        }
    }

    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}
