package app.ch.base.test.matcher

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedDiagnosingMatcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Description
import org.hamcrest.Matcher

fun hasItemAtPosition(position: Int, matcher: Matcher<View>): Matcher<View> {
    return object : BoundedDiagnosingMatcher<View, RecyclerView>(RecyclerView::class.java) {

        override fun matchesSafely(item: RecyclerView, description: Description): Boolean {
            val viewHolder = item.findViewHolderForAdapterPosition(position)
            return matcher.matches(viewHolder?.itemView)
        }

        override fun describeMoreTo(description: Description) {
            description.appendText("has item at position $position : ")
            matcher.describeTo(description)
        }
    }
}

fun hasItemCount(itemCount: Int): Matcher<View> {
    return object : BoundedDiagnosingMatcher<View, RecyclerView>(RecyclerView::class.java) {

        override fun matchesSafely(item: RecyclerView, mismatchDescription: Description?): Boolean {
            return item.adapter?.itemCount == itemCount
        }

        override fun describeMoreTo(description: Description) {
            description.appendText("has $itemCount items")
        }
    }
}

fun typeSearchViewText(text: String): ViewAction {
    return object : ViewAction {

        override fun getDescription(): String {
            return "Change view text"
        }

        override fun getConstraints(): Matcher<View> {
            return isAssignableFrom(SearchView::class.java)
        }

        override fun perform(uiController: UiController?, view: View?) {
            (view as SearchView).setQuery(text, false)
        }
    }
}

fun clickChildViewWithId(id: Int): ViewAction {
    return object : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return isAssignableFrom(View::class.java)
        }

        override fun getDescription(): String {
            return "Click on a child view with specified id."
        }

        override fun perform(uiController: UiController, view: View) {
            view.findViewById<View>(id).performClick()
        }
    }
}
