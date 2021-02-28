package app.ch.weatherapp

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
inline fun <T> Flow<T>.test(
    crossinline testAction: (List<T>) -> Unit
) {

    runBlockingTest {
        val job = launch {
            collect()
            testAction(toList(mutableListOf()))
        }

        job.cancel()
    }
}
