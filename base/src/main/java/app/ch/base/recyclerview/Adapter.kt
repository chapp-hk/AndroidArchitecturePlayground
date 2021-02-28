package app.ch.base.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Reduce RecyclerView Adapter boilerplate by using DataBinding
 */

class RecyclerViewAdapter<T : ListItem>(
    private val itemVariableId: Int,
    private val owner: LifecycleOwner,
    callback: DiffUtil.ItemCallback<T> = SimpleDiffUtilItemCallback(),
) : ListAdapter<T, DataBindingRecyclerViewHolder>(callback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DataBindingRecyclerViewHolder {
        return DataBindingRecyclerViewHolder(getBinding(viewType, parent))
    }

    override fun onBindViewHolder(
        holder: DataBindingRecyclerViewHolder,
        position: Int,
    ) {
        holder.binding.apply {
            lifecycleOwner = owner
            setVariable(itemVariableId, getItem(position))
            executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId
    }

    private fun getBinding(
        @LayoutRes layoutRes: Int,
        parent: ViewGroup,
    ): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes,
            parent,
            false
        )
    }
}

class PagingAdapter(
    private val itemVariableId: Int,
    private val owner: LifecycleOwner,
    callback: DiffUtil.ItemCallback<ListItem> = SimpleDiffUtilItemCallback(),
) : PagingDataAdapter<ListItem, DataBindingRecyclerViewHolder>(callback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DataBindingRecyclerViewHolder {
        return DataBindingRecyclerViewHolder(getBinding(viewType, parent))
    }

    override fun onBindViewHolder(
        holder: DataBindingRecyclerViewHolder,
        position: Int,
    ) {
        holder.binding.apply {
            lifecycleOwner = owner
            setVariable(itemVariableId, getItem(position))
            executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.layoutId ?: 0
    }

    private fun getBinding(
        @LayoutRes layoutRes: Int,
        parent: ViewGroup,
    ): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes,
            parent,
            false
        )
    }
}

/**
 * RecyclerViewHoler using DataBinding
 *
 * @property binding the DataBinding instance
 */
class DataBindingRecyclerViewHolder(
    val binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root)

/**
 * Default DiffUtil.ItemCallback
 */
class SimpleDiffUtilItemCallback<T : ListItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}

fun Fragment.pagingAdapter(
    itemVariableId: Int
): Lazy<PagingAdapter> {
    return lazy { PagingAdapter(itemVariableId, viewLifecycleOwner) }
}
