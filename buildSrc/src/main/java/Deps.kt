/**
 * common version numbers
 */
object Versions {
    const val androidGradlePlugin = "4.1.2"
    const val kotlin = "1.4.31"
    const val kotlinxCoroutines = "1.4.2"
    const val androidxLifecycle = "2.3.0"
    const val hilt = "2.33-beta"
    const val dagger = "2.33"
    const val navigation = "2.3.3"
    const val room = "2.3.0-beta02"
    const val paging = "3.0.0-beta01"
    const val okhttp = "5.0.0-alpha.2"
    const val androidxTest = "1.3.0"
    const val mockk = "1.10.6"
}

object GradlePlugins {
    const val android = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

object Deps {
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val strikt = "io.strikt:strikt-core:0.29.0"
    const val livedataTesting = "com.jraska.livedata:testing-ktx:1.1.2"
    const val fresco = "com.facebook.fresco:fresco:2.4.0"

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }

    object Kotlinx {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0"

        object Coroutines {
            const val core =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
            const val playServices =
                "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.kotlinxCoroutines}"
            const val test =
                "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutines}"
        }
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.5.0-beta02"
        const val appCompat = "androidx.appcompat:appcompat:1.3.0-beta01"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"

        object Arch {
            object Core {
                const val testing = "androidx.arch.core:core-testing:2.1.0"
            }
        }

        object Lifecycle {
            const val runtime =
                "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycle}"
            const val liveData =
                "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidxLifecycle}"
            const val viewModel =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycle}"
        }

        object Navigation {
            const val fragment =
                "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
            const val ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:${Versions.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.room}"
            const val ktx = "androidx.room:room-ktx:${Versions.room}"
            const val testing = "androidx.room:room-testing:${Versions.room}"
        }

        object Paging {
            const val runtime = "androidx.paging:paging-runtime:${Versions.paging}"
            const val common = "androidx.paging:paging-common:${Versions.paging}"
        }

        object Test {
            const val extJunit = "androidx.test.ext:junit:1.1.3-alpha04"
            const val runner = "androidx.test:runner:${Versions.androidxTest}"
            const val rules = "androidx.test:rules:${Versions.androidxTest}"

            object Espresso {
                const val core = "androidx.test.espresso:espresso-core:3.3.0"
            }
        }
    }

    object Android {
        const val material = "com.google.android.material:material:1.4.0-alpha01"

        object Gms {
            const val playServicesLocation = "com.google.android.gms:play-services-location:18.0.0"
        }
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    }

    object Dagger {
        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    }

    object Okhttp {
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val kotlinxSerializationConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    }

    object Junit {
        const val junit = "junit:junit:4.13.2"
    }

    object MockK {
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
        const val android = "io.mockk:mockk-android:${Versions.mockk}"
    }
}
