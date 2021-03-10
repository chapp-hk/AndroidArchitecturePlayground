plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig()
android {
    defaultConfig {
        testInstrumentationRunner = AppConfig.defaultInstrumentationRunner
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(project(mapOf("path" to ":data")))

    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.timber)

    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)

    implementation(Deps.Android.Gms.playServicesLocation)
    implementation(Deps.Kotlinx.Coroutines.playServices)

    androidTestImplementation(project(mapOf("path" to ":base-test")))
    androidTestImplementation(Deps.AndroidX.Test.runner)
    androidTestImplementation(Deps.AndroidX.Test.extJunit)
    androidTestImplementation(Deps.AndroidX.Test.rules)
    androidTestImplementation(Deps.Kotlinx.Coroutines.test)
    androidTestImplementation(Deps.MockK.android)
}
