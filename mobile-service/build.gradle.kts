plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig()

dependencies {
    implementation(project(mapOf("path" to ":data")))

    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.timber)

    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)

    implementation(Deps.Android.Gms.playServicesLocation)
    implementation(Deps.Kotlinx.Coroutines.playServices)

    androidTestImplementation(project(mapOf("path" to ":base-test")))
    androidTestImplementation(Deps.Hilt.testing)
    kaptAndroidTest(Deps.Hilt.compiler)
    androidTestImplementation(Deps.AndroidX.Test.runner)
    androidTestImplementation(Deps.AndroidX.Test.extJunit)
    androidTestImplementation(Deps.AndroidX.Test.rules)
    androidTestImplementation(Deps.Kotlinx.Coroutines.test)
    androidTestImplementation(Deps.MockK.android)
}
