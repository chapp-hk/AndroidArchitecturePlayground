package app.ch.base.test.data.remote

import java.io.InputStreamReader

object FileReader {
    fun get(fileName: String): String {
        return InputStreamReader(javaClass.classLoader?.getResourceAsStream(fileName))
            .use { it.readText() }
    }
}
