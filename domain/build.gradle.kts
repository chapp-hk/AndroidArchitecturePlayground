plugins {
    id("java-library")
    id("kotlin")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlinOptionsJvmTarget()

dependencies {
    implementation(Deps.Kotlin.stdlib)

    implementation(Deps.Dagger.dagger)
    kapt(Deps.Dagger.compiler)

    implementation(Deps.Kotlinx.Coroutines.core)
    implementation(Deps.AndroidX.Paging.common)

    testImplementation(Deps.Junit.junit)
    testImplementation(Deps.MockK.mockk)
    testImplementation(Deps.Kotlinx.Coroutines.test)
}
