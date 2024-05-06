package dev.wiskiw.sunnysideapp.app

import dev.wiskiw.shared.utils.buildfields.ApiKeysFields
import dev.wiskiw.shared.utils.buildfields.BuildConfigFields
import dev.wiskiw.shared.utils.buildfields.BuildFieldsProvider
import dev.wiskiw.sunnysideapp.BuildConfig

class SunnySideAppBuildFieldsProvider : BuildFieldsProvider {
    override fun getBuildConfig() = BuildConfigFields(
        buildType = BuildConfig.BUILD_TYPE,
        versionCode = BuildConfig.VERSION_CODE,
        versionName = BuildConfig.VERSION_NAME,
    )

    override fun getApiKeys() = ApiKeysFields(
        openWeatherMapApiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY,
    )
}
