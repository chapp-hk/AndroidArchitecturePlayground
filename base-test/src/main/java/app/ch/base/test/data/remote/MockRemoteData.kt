package app.ch.base.test.data.remote

import java.io.InputStreamReader

object MockRemoteData {
    fun fromFile(fileName: String): String {
        return InputStreamReader(javaClass.classLoader?.getResourceAsStream(fileName))
            .use { it.readText() }
    }
}
