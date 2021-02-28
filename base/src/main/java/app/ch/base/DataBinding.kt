package app.ch.base

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("isVisible")
fun View.setIsVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("itemDecoration")
fun RecyclerView.setItemDecoration(orientation: Int) {
    addItemDecoration(DividerItemDecoration(context, orientation))
}
