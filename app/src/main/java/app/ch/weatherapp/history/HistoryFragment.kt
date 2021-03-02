package app.ch.weatherapp.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.ch.base.recyclerview.pagingAdapter
import app.ch.weatherapp.BR
import app.ch.weatherapp.R
import app.ch.weatherapp.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val viewModel by viewModels<HistoryViewModel>()
    private val adapter by pagingAdapter<HistoryListItem>(BR.listItem)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        setupViews(view)
        setupEventObservers()
    }

    private fun setupViews(view: View) {
        FragmentHistoryBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
            it.recyclerView.adapter = adapter
        }
    }

    private fun setupEventObservers() {
        viewModel.queryWeatherHistory()
            .onEach { adapter.submitData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.deleteItemEvent
            .onEach {
                viewModel.deleteItem(it)
                adapter.refresh()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
