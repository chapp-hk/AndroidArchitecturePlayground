package app.ch.base.test

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.testing.CustomTestApplication

open class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}

@CustomTestApplication(TestApp::class)
interface HiltTestApplication

// A custom runner to set up the instrumented application class for tests.
class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return Instrumentation.newApplication(HiltTestApplication_Application::class.java, context)
    }
}

