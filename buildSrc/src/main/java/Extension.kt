import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.androidApplicationConfig(
    appId: String,
    appVersionCode: Int = AppConfig.versionCode,
    appVersionName: String = AppConfig.versionName
) {
    androidLibraryConfig {
        applicationId = appId
        versionCode = appVersionCode
        versionName = appVersionName
    }
}

fun Project.androidLibraryConfig(defaultConfigExtensions: (DefaultConfig.() -> Unit)? = null) {
    android.run {
        compileSdkVersion(AppConfig.compileSdk)
        defaultConfig {
            defaultConfigExtensions?.invoke(this)
            minSdkVersion(AppConfig.minSdk)
            targetSdkVersion(AppConfig.targetSdk)
            testInstrumentationRunner = AppConfig.testInstrumentationRunner
        }

        buildTypes {
            getByName("debug") {
                isMinifyEnabled = false
            }
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
                consumerProguardFiles("consumer-rules.pro")
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        kotlinCompileOptions()

        packagingOptions {
            exclude("META-INF/AL2.0")
            exclude("META-INF/LGPL2.1")
        }

        lintOptions {
            isIgnoreWarnings = false
        }
    }
}

fun Project.kotlinCompileOptions(target: String = JavaVersion.VERSION_1_8.toString()) {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = target
            freeCompilerArgs = freeCompilerArgs + arrayOf(
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
            )
        }
    }
}

val Project.android: BaseExtension
    get() = extensions.findByName("android") as? BaseExtension
        ?: error("Project '$name' is not an Android module")
