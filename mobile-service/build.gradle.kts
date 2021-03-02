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

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
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
