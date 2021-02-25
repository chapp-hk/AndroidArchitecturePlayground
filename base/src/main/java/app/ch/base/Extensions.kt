package app.ch.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
}

fun Fragment.hideKeyboard() {
    requireActivity().hideKeyboard()
}
