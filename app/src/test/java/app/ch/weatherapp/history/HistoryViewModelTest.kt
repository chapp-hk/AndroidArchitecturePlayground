package app.ch.weatherapp.history

import app.ch.domain.weather.usecase.GetWeatherHistoryUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class HistoryViewModelTest {

    @MockK
    private lateinit var getWeatherHistory: GetWeatherHistoryUseCase

    private lateinit var historyViewModel: HistoryViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        historyViewModel = HistoryViewModel(getWeatherHistory)
    }

    @Test
    fun queryWeatherHistory() {
        historyViewModel.queryWeatherHistory()

        verify(exactly = 1) {
            getWeatherHistory()
        }
    }
}
