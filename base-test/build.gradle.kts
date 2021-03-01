plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlinx.Coroutines.test)
}
