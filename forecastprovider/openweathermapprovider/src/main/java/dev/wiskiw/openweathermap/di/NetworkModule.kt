package dev.wiskiw.openweathermap.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val KTOR_LOG_TAG = "OWM Http"

    private val TIME_OUT = TimeUnit.SECONDS.toMillis(60).toInt()

    @OpenWeatherMapModule.OpenWetaherMapHttpClient
    @Provides
    @Singleton
    fun provideOpenWeatherMapKtorHttpJsonClient(
        buildFieldsProvider: BuildFieldsProvider,
    ): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
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
                    Log.d(KTOR_LOG_TAG, message)
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
}
