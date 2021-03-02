package app.ch.weatherapp.history

import app.ch.base.test.test
import app.ch.domain.weather.usecase.DeleteAllWeatherUseCase
import app.ch.domain.weather.usecase.DeleteWeatherUseCase
import app.ch.domain.weather.usecase.GetWeatherHistoryUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isNotEmpty

@ExperimentalCoroutinesApi
class HistoryViewModelTest {

    @MockK
    private lateinit var getWeatherHistory: GetWeatherHistoryUseCase

    @MockK
    private lateinit var deleteWeather: DeleteWeatherUseCase

    @MockK
    private lateinit var deleteAllWeather: DeleteAllWeatherUseCase

    private lateinit var historyViewModel: HistoryViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        historyViewModel = HistoryViewModel(
            getWeatherHistory,
            deleteWeather,
            deleteAllWeather,
        )
    }

    @Test
    fun queryWeatherHistory() {
        historyViewModel.queryWeatherHistory()

        verify(exactly = 1) {
            getWeatherHistory()
        }
    }

    @Test
    fun deleteItem() {
        historyViewModel.deleteItem(8964)

        verify(exactly = 1) {
            deleteWeather(8964)
        }

        historyViewModel.historyEvent.test {
            expectThat(it.first()).isA<HistoryEvent.ListChanged>()
        }
    }

    @Test
    fun deleteAllItems() {
        historyViewModel.deleteAllItems()

        verify(exactly = 1) {
            deleteAllWeather()
        }

        historyViewModel.historyEvent.test {
            expectThat(it.first()).isA<HistoryEvent.ListChanged>()
        }
    }
}
