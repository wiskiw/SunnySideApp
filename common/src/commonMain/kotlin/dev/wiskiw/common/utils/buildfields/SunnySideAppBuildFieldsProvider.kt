package dev.wiskiw.common.utils.buildfields

import dev.wiskiw.common.BuildKonfig

class SunnySideAppBuildFieldsProvider : BuildFieldsProvider {

    // todo clean up

//    override fun getBuildConfig() = BuildConfigFields(
//        buildType = BuildKonfig.BUILD_TYPE,
//        versionCode = BuildKonfig.VERSION_CODE,
//        versionName = BuildKonfig.VERSION_NAME,
//    )

    override fun getApiKeys() = ApiKeysFields(
        openWeatherMapApiKey = BuildKonfig.OPEN_WEATHER_MAP_API_KEY,
    )
}
