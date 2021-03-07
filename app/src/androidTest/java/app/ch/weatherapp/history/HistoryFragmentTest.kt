package app.ch.weatherapp.history

import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import app.ch.base.test.data.local.populateWeatherData
import app.ch.base.test.matcher.hasItemCount
import app.ch.base.test.rule.DisableAnimationsRule
import app.ch.data.base.local.DaoProvider
import app.ch.weatherapp.R
import app.ch.weatherapp.launchFragmentInHiltContainer
import app.ch.weatherapp.launchNavFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HistoryFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val disableAnimationsRule = DisableAnimationsRule()

    @Inject
    lateinit var daoProvider: DaoProvider

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun empty_history() {
        launchFragmentInHiltContainer<HistoryFragment>()

        Thread.sleep(2000)

        onView(withId(R.id.tvWelcome))
            .check(matches(isDisplayed()))
    }

    @Test
    fun show_history() {
        daoProvider.populateWeatherData()

        launchFragmentInHiltContainer<HistoryFragment>()

        Thread.sleep(2000)

        onView(withId(R.id.recyclerView))
            .check(matches(hasItemCount(3)))
    }

    @Test
    fun history_item_click() {
        daoProvider.populateWeatherData()

        var resultRequestKey = ""
        var resultCityName = ""

        launchNavFragment<HistoryFragment>(navController) {
            navController.setCurrentDestination(R.id.history)

            parentFragmentManager.setFragmentResultListener(
                REQUEST_DISPLAY_CITY,
                viewLifecycleOwner
            ) { requestKey, bundle ->
                resultRequestKey = requestKey
                resultCityName = bundle.getString(KEY_CITY_NAME, "")
            }
        }

        Thread.sleep(2000)

        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )

        expectThat(resultRequestKey).isEqualTo(REQUEST_DISPLAY_CITY)
        expectThat(resultCityName).isEqualTo("Hong Kong")
    }
}
