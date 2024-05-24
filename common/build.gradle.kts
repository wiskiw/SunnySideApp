import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildKonfig)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "common"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    // Workaround for "Cannot locate tasks that match ':shared:testClasses' as task 'testClasses' not found in project ':shared'."
    // https://stackoverflow.com/a/78159011
    task("testClasses")
}

android {
    namespace = "dev.wiskiw.common"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

buildkonfig {
    packageName = "dev.wiskiw.common"

    defaultConfigs {
        val keystoreFile = project.rootProject.file("apikeys.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        buildConfigField(
            type = STRING,
            name = "OPEN_WEATHER_MAP_API_KEY",
            value = properties.getProperty("openweathermap.apikey") ?: ""
        )

        buildConfigField(
            type = STRING,
            name = "WEATHER_BIT_API_KEY",
            value = properties.getProperty("weatherbit.apikey") ?: ""
        )

        buildConfigField(
            type = STRING,
            name = "METEO_SOURCE_API_KEY",
            value = properties.getProperty("meteosource.apikey") ?: ""
        )
    }
}
