plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version Versions.kotlin
}

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"${EnvConfig.Debug.baseUrl}\"")
            buildConfigField("String", "API_KEY", "\"${EnvConfig.Debug.apiKey}\"")

            isMinifyEnabled = false
        }

        getByName("release") {
            buildConfigField("String", "BASE_URL", "\"${EnvConfig.Release.baseUrl}\"")
            buildConfigField("String", "API_KEY", "\"${EnvConfig.Release.apiKey}\"")

            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(mapOf("path" to ":domain")))

    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.timber)

    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)

    implementation(Deps.Okhttp.okhttp)
    implementation(Deps.Okhttp.loggingInterceptor)
    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.kotlinxSerializationConverter)

    implementation(Deps.Kotlinx.serialization)
    implementation(Deps.Kotlinx.Coroutines.core)

    implementation(Deps.AndroidX.Room.runtime)
    kapt(Deps.AndroidX.Room.compiler)
    implementation(Deps.AndroidX.Room.ktx)

    implementation(Deps.AndroidX.Paging.runtime)

    testImplementation(Deps.Junit.junit)
    testImplementation(Deps.MockK.mockk)
    testImplementation(Deps.strikt)
    testImplementation(Deps.Kotlinx.Coroutines.test)

    androidTestImplementation(Deps.AndroidX.Test.runner)
    androidTestImplementation(Deps.AndroidX.Test.extJunit)
    androidTestImplementation(Deps.AndroidX.Arch.Core.testing)
    androidTestImplementation(Deps.Kotlinx.Coroutines.test)
    androidTestImplementation(Deps.strikt)
}
