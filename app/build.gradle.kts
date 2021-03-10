plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.github.ben-manes.versions") version Versions.versionPlugin
    kotlin("android")
    kotlin("kapt")
}

androidApplicationConfig(AppConfig.applicationId)
android {
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

    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":base")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":mobile-service")))

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

    implementation(Deps.AndroidX.Paging.runtime)
    implementation(Deps.fresco)

    testImplementation(project(mapOf("path" to ":base-test")))
    testImplementation(Deps.Junit.junit)
    testImplementation(Deps.MockK.mockk)
    testImplementation(Deps.strikt)
    testImplementation(Deps.Kotlinx.Coroutines.test)
    testImplementation(Deps.AndroidX.Arch.Core.testing)
    testImplementation(Deps.livedataTesting)

    androidTestImplementation(project(mapOf("path" to ":base-test")))
    androidTestImplementation(Deps.AndroidX.Test.extJunit)
    androidTestImplementation(Deps.AndroidX.Test.Espresso.core)
    androidTestImplementation(Deps.AndroidX.Test.Espresso.contrib)
    androidTestImplementation(Deps.AndroidX.Navigation.testing)
    androidTestImplementation(Deps.AndroidX.Test.rules)
    androidTestImplementation(Deps.AndroidX.Arch.Core.testing)
    // Once https://issuetracker.google.com/127986458 is fixed this can be testImplementation
    debugImplementation(Deps.AndroidX.fragmentTesting)
    androidTestImplementation(Deps.strikt)

    androidTestImplementation(Deps.Hilt.testing)
    kaptAndroidTest(Deps.Hilt.compiler)

    androidTestImplementation(Deps.AndroidX.Room.runtime)
    androidTestImplementation(Deps.Okhttp.mockWebServer)
}
