package app.ch.base.test.matcher

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

fun typeSearchViewText(text: String): ViewAction {
    return object : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(SearchView::class.java)
        }

        override fun getDescription(): String {
            return "Change view text"
        }

        override fun perform(uiController: UiController?, view: View?) {
            (view as SearchView).setQuery(text, false)
        }
    }
}

fun clickChildViewWithId(id: Int): ViewAction {
    return object : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(View::class.java)
        }

        override fun getDescription(): String {
            return "Click on a child view with specified id."
        }

        override fun perform(uiController: UiController, view: View) {
            view.findViewById<View>(id).performClick()
        }
    }
}
