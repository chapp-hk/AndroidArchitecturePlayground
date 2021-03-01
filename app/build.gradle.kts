plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.github.ben-manes.versions") version "0.36.0"
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
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

    buildFeatures {
        buildConfig = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":base")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":gms")))

    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.timber)
    implementation(Deps.Android.material)

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appCompat)
    implementation(Deps.AndroidX.fragment)

    implementation(Deps.AndroidX.Navigation.fragment)
    implementation(Deps.AndroidX.Navigation.ui)

    implementation(Deps.AndroidX.Lifecycle.runtime)
    implementation(Deps.AndroidX.Lifecycle.liveData)
    implementation(Deps.AndroidX.Lifecycle.viewModel)

    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)

    implementation(Deps.Kotlinx.serialization)
    implementation(Deps.Okhttp.loggingInterceptor)
    implementation(Deps.Retrofit.kotlinxSerializationConverter)
    implementation(Deps.AndroidX.Room.runtime)

    implementation(Deps.AndroidX.Paging.runtime)

    implementation(Deps.Android.Gms.playServicesLocation)

    testImplementation(project(mapOf("path" to ":base-test")))
    testImplementation(Deps.Junit.junit)
    testImplementation(Deps.MockK.mockk)
    testImplementation(Deps.strikt)
    testImplementation(Deps.Kotlinx.Coroutines.test)
    testImplementation(Deps.AndroidX.Arch.Core.testing)
    testImplementation(Deps.livedataTesting)

    androidTestImplementation(Deps.AndroidX.Test.extJunit)
    androidTestImplementation(Deps.AndroidX.Test.Espresso.core)
}
