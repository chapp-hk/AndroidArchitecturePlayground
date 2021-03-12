package app.ch.weatherapp.weather

import android.Manifest
import android.view.KeyEvent
import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import app.ch.base.test.data.local.populateWeatherData
import app.ch.base.test.matcher.hasItemAtPosition
import app.ch.base.test.matcher.typeSearchViewText
import app.ch.base.test.rule.DisableAnimationsRule
import app.ch.base.test.rule.MockWebServerRule
import app.ch.data.base.local.DaoProvider
import app.ch.weatherapp.R
import app.ch.weatherapp.history.KEY_CITY_NAME
import app.ch.weatherapp.history.REQUEST_DISPLAY_CITY
import app.ch.weatherapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class WeatherFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val disableAnimationsRule = DisableAnimationsRule()

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    var runtimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION)

    @Inject
    lateinit var daoProvider: DaoProvider

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun empty_history() {
        launchFragmentInHiltContainer<WeatherFragment>()

        onView(withId(R.id.tvWelcome)).check(matches(isDisplayed()))
    }

    @Test
    fun show_latest_history() {
        daoProvider.populateWeatherData()

        launchFragmentInHiltContainer<WeatherFragment>()

        onView(withText("Hong Kong")).check(matches(isDisplayed()))
        onView(withText(R.string.weather_feels_like)).check(matches(isDisplayed()))
        onView(withText(R.string.weather_max_temp)).check(matches(isDisplayed()))
        onView(withText(R.string.weather_min_temp)).check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).check(
            matches(
                hasItemAtPosition(
                    0, allOf(
                        hasDescendant(withText("Clear sky")),
                    )
                )
            )
        )
    }

    @Test
    fun search_by_city_name() {
        mockWebServerRule.mockSuccess("response-search-city-name.json")

        launchFragmentInHiltContainer<WeatherFragment>()

        onView(withId(R.id.searchView)).perform(
            click(),
            typeSearchViewText("Moscow"),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )

        Thread.sleep(2000)

        onView(withId(R.id.tvCityName))
            .check(matches(withText("Moscow")))

        onView(withText(R.string.weather_feels_like))
            .check(matches(isDisplayed()))

        onView(withText(R.string.weather_max_temp))
            .check(matches(isDisplayed()))

        onView(withText(R.string.weather_min_temp))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).check(
            matches(
                hasItemAtPosition(
                    0, allOf(
                        hasDescendant(withText("clear sky")),
                    )
                )
            )
        )
    }

    @Test
    fun search_from_fragment_result() {
        daoProvider.populateWeatherData()
        mockWebServerRule.mockSuccess("response-search-city-name.json")

        launchFragmentInHiltContainer<WeatherFragment> {
            parentFragmentManager.setFragmentResult(
                REQUEST_DISPLAY_CITY,
                bundleOf(KEY_CITY_NAME to "moscow")
            )
        }

        Thread.sleep(2000)

        onView(withId(R.id.tvCityName))
            .check(matches(withText("Moscow")))

        onView(withText(R.string.weather_feels_like))
            .check(matches(isDisplayed()))

        onView(withText(R.string.weather_max_temp))
            .check(matches(isDisplayed()))

        onView(withText(R.string.weather_min_temp))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).check(
            matches(
                hasItemAtPosition(
                    0, allOf(
                        hasDescendant(withText("clear sky")),
                    )
                )
            )
        )
    }

    @Test
    fun search_by_location() {
        daoProvider.populateWeatherData()
        mockWebServerRule.mockSuccess("response-search-location.json")

        launchFragmentInHiltContainer<WeatherFragment>()

        onView(withId(R.id.btnLocation)).perform(click())

        Thread.sleep(2000)

        onView(withId(R.id.tvCityName))
            .check(matches(withText("London")))

        onView(withText(R.string.weather_feels_like))
            .check(matches(isDisplayed()))

        onView(withText(R.string.weather_max_temp))
            .check(matches(isDisplayed()))

        onView(withText(R.string.weather_min_temp))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).check(
            matches(
                hasItemAtPosition(
                    0, allOf(
                        hasDescendant(withText("broken clouds")),
                    )
                )
            )
        )
    }

    @Test
    fun network_error() {
        mockWebServerRule.mockError(HttpURLConnection.HTTP_UNAUTHORIZED)

        launchFragmentInHiltContainer<WeatherFragment>()

        onView(withId(R.id.btnLocation)).perform(click())

        onView(withText(R.string.weather_error_access_denied))
            .check(matches(isDisplayed()))
    }

    @Test
    fun not_found_error() {
        mockWebServerRule.mockError(HttpURLConnection.HTTP_NOT_FOUND)

        launchFragmentInHiltContainer<WeatherFragment>()

        onView(withId(R.id.btnLocation)).perform(click())

        onView(withText(R.string.weather_error_not_found))
            .check(matches(isDisplayed()))
    }

    @Test
    fun limit_exceeded_error() {
        mockWebServerRule.mockError(429)

        launchFragmentInHiltContainer<WeatherFragment>()

        onView(withId(R.id.btnLocation)).perform(click())

        onView(withText(R.string.weather_error_limit_exceeded))
            .check(matches(isDisplayed()))
    }

    @Test
    fun unknown_network_error() {
        mockWebServerRule.mockError(HttpURLConnection.HTTP_BAD_REQUEST)

        launchFragmentInHiltContainer<WeatherFragment>()

        onView(withId(R.id.btnLocation)).perform(click())

        onView(withText(R.string.weather_error_unknown))
            .check(matches(isDisplayed()))
    }
}
