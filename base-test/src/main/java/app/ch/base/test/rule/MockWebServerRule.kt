package app.ch.base.test.rule

import app.ch.base.test.data.remote.FileReader
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule : TestWatcher() {

    private val mockWebServer = MockWebServer()

    override fun starting(description: Description?) {
        super.starting(description)
        mockWebServer.start(8080)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        mockWebServer.shutdown()
    }

    fun mockSuccess(fileName: String) {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(200).setBody(FileReader.get(fileName))
            }
        }
    }

    fun mockError(errorCode: Int) {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(errorCode)
            }
        }
    }
}
