package app.ch.weatherapp.history

import app.ch.base.test.test
import app.ch.weatherapp.weather.mock.MockData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo

@ExperimentalCoroutinesApi
class HistoryListItemTest {

    private val event = MutableSharedFlow<HistoryEvent>()
    private val coroutineScope = TestCoroutineScope()
    private val historyListItem = MockData.weatherEntity.toUiModel(event, coroutineScope)

    @Test
    fun delete() {
        historyListItem.delete()

        event.test {
            expectThat(it.first())
                .isA<HistoryEvent.DeleteItem>()
                .get { id }
                .isEqualTo(historyListItem.id)
        }
    }

    @Test
    fun display() {
        historyListItem.display()

        event.test {
            expectThat(it.first())
                .isA<HistoryEvent.Display>()
                .get { cityName }
                .isEqualTo(historyListItem.cityName)
        }
    }
}
