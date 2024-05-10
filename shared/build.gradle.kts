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
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)

            implementation(libs.kotlinx.coroutines.core)

            implementation(projects.common)
            implementation(projects.forecastprovider.fakeforecastprovider)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.play.services.location)
            implementation(libs.koin.core)

//            implementation(projects.forecastprovider.realforecastprovider)
        }

        iosMain.dependencies {
            implementation(libs.koin.core)
        }
    }

    // Workaround for "Cannot locate tasks that match ':shared:testClasses' as task 'testClasses' not found in project ':shared'."
    // https://stackoverflow.com/a/78159011
    task("testClasses")
}

android {
    namespace = "dev.wiskiw.shared"
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
    packageName = "dev.wiskiw.shared"

    defaultConfigs {
        val keystoreFile = project.rootProject.file("apikeys.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        buildConfigField(
            type = STRING,
            name = "OPEN_WEATHER_MAP_API_KEY",
            value = properties.getProperty("openweathermap.apikey") ?: ""
        )
    }
}
