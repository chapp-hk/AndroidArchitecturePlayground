package app.ch.base.recyclerview

import androidx.fragment.app.Fragment

inline fun <reified T : ListItem> Fragment.recyclerViewAdapter(
    itemVariableId: Int
): Lazy<RecyclerViewAdapter<T>> {
    return lazy { RecyclerViewAdapter(itemVariableId, viewLifecycleOwner) }
}

inline fun <reified T : ListItem> Fragment.pagingAdapter(
    itemVariableId: Int
): Lazy<PagingAdapter<T>> {
    return lazy { PagingAdapter(itemVariableId, viewLifecycleOwner) }
}
