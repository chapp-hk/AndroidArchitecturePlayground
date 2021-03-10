plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig()

dependencies {
    implementation(project(mapOf("path" to ":data")))

    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlinx.Coroutines.test)
    implementation(Deps.AndroidX.Test.extJunit)
    implementation(Deps.AndroidX.Test.Espresso.core)
    implementation(Deps.AndroidX.Test.uiautomator)

    implementation(Deps.Hilt.android)
    implementation(Deps.Hilt.testing)
    kapt(Deps.Hilt.compiler)

    implementation(Deps.fresco)
    implementation(Deps.AndroidX.appCompat)
    implementation(Deps.AndroidX.Room.runtime)
    implementation(Deps.AndroidX.recyclerview)
    implementation(Deps.Okhttp.mockWebServer)
}
