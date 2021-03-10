plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

androidLibraryConfig()
android {
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Android.material)
    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.fragment)
    implementation(Deps.AndroidX.recyclerview)
    implementation(Deps.AndroidX.Paging.runtime)
}
