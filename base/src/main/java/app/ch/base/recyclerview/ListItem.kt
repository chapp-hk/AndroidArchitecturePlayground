package app.ch.base.recyclerview

import androidx.annotation.LayoutRes

interface ListItem {

    @get:LayoutRes
    val layoutId: Int
}
