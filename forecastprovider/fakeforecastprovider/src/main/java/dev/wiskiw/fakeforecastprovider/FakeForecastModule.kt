package dev.wiskiw.fakeforecastprovider

import dev.wiskiw.shared.data.ForecastRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

object Named {
    val FAKE_FORECAST_REPOSITORY = named("FAKE_FORECAST_REPOSITORY")
}

val fakeForecastModule = module {
    single<ForecastRepository>(Named.FAKE_FORECAST_REPOSITORY) {
        FakeForecastRepository()
    }
}
