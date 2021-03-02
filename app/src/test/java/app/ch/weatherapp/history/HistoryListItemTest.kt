package app.ch.weatherapp.history

import app.ch.weatherapp.weather.mock.MockData
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class HistoryListItemTest {

    private val deleteEvent = MutableStateFlow(Long.MIN_VALUE)
    private val historyListItem = MockData.weatherEntity.toUiModel(deleteEvent)

    @Test
    fun delete() {
        historyListItem.delete()

        expectThat(deleteEvent.value).isEqualTo(MockData.weatherEntity.id)
    }
}
