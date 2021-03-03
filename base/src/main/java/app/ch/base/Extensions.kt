package app.ch.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Activity.hideKeyboard() {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
}

fun Fragment.hideKeyboard() {
    requireActivity().hideKeyboard()
}

fun showSnackBar(
    view: View,
    @StringRes messageResId: Int? = null,
    @StringRes actionResId: Int? = null,
    action: () -> Unit = {},
) {
    requireNotNull(messageResId)
    Snackbar.make(
        view,
        messageResId,
        Snackbar.LENGTH_LONG
    ).apply {
        actionResId?.let {
            setAction(it) { action() }
        }
    }.show()
}

inline fun <reified T : ViewDataBinding> Fragment.getBinding(): T {
    return requireNotNull(DataBindingUtil.getBinding(requireView()))
}
