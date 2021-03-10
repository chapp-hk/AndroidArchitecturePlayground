plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version Versions.kotlin
}

androidLibraryConfig()
android {
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"${EnvConfig.Debug.baseUrl}\"")
            buildConfigField("String", "API_KEY", "\"${EnvConfig.Debug.apiKey}\"")
            buildConfigField("String", "ICON_URL", "\"${EnvConfig.Debug.iconUrl}\"")
        }

        getByName("release") {
            buildConfigField("String", "BASE_URL", "\"${EnvConfig.Release.baseUrl}\"")
            buildConfigField("String", "API_KEY", "\"${EnvConfig.Release.apiKey}\"")
            buildConfigField("String", "ICON_URL", "\"${EnvConfig.Release.iconUrl}\"")
        }
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
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

    testImplementation(project(mapOf("path" to ":base-test")))
    testImplementation(Deps.Junit.junit)
    testImplementation(Deps.MockK.mockk)
    testImplementation(Deps.strikt)
    testImplementation(Deps.Kotlinx.Coroutines.test)

    androidTestImplementation(project(mapOf("path" to ":base-test")))
    androidTestImplementation(Deps.AndroidX.Test.runner)
    androidTestImplementation(Deps.AndroidX.Test.extJunit)
    androidTestImplementation(Deps.AndroidX.Arch.Core.testing)
    androidTestImplementation(Deps.Kotlinx.Coroutines.test)
    androidTestImplementation(Deps.strikt)
    androidTestImplementation(Deps.Hilt.testing)
    kaptAndroidTest(Deps.Hilt.compiler)
}
