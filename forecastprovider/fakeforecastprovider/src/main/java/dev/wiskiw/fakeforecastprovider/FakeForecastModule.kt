package dev.wiskiw.fakeforecastprovider

import dev.wiskiw.shared.data.ForecastRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val namedFakeForecastRepository = named("namedFakeForecastRepository")

val fakeForecastModule = module {
    single<ForecastRepository>(namedFakeForecastRepository) {
        FakeForecastRepository()
    }
}
