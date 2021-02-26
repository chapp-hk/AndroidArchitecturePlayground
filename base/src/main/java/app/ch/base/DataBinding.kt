package app.ch.base

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun View.setIsVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}
