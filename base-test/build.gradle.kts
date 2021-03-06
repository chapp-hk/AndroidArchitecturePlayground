plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(mapOf("path" to ":data")))

    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlinx.Coroutines.test)
    implementation(Deps.AndroidX.Test.extJunit)
    implementation(Deps.AndroidX.Test.Espresso.core)
    implementation(Deps.Hilt.android)
    implementation(Deps.Hilt.testing)
    kapt(Deps.Hilt.compiler)
    implementation(Deps.fresco)
    implementation(Deps.AndroidX.Room.runtime)
}
