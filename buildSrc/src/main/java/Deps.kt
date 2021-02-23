/**
 * common version numbers
 */
object Versions {
    const val androidGradlePlugin = "4.1.2"
    const val kotlin = "1.4.30"
    const val androidxLifecycle = "2.3.0"
    const val hilt = "2.32-alpha"
    const val navigation = "2.3.3"
}

object GradlePlugins {
    const val android = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

object Deps {
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.3.2"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.0"

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

        object Test {
            const val extJunit = "androidx.test.ext:junit:1.1.2"

            object Espresso {
                const val core = "androidx.test.espresso:espresso-core:3.3.0"
            }
        }
    }

    object Android {
        const val material = "com.google.android.material:material:1.3.0"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    }

    object Junit {
        const val junit = "junit:junit:4.13.2"
    }
}
