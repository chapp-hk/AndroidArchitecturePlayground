package app.ch.weatherapp.weather

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.ch.base.hideKeyboard
import app.ch.domain.base.ErrorEntity
import app.ch.weatherapp.R
import app.ch.weatherapp.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val viewModel by viewModels<WeatherViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::handleRequestPermissionResult
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupEventObservers()
    }

    private fun setupViews(view: View) {
        FragmentWeatherBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
            it.etSearch.setOnEditorActionListener { _, _, _ ->
                viewModel.queryWeatherByCityName().let { true }
            }
            it.btnLocation.setOnClickListener {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    private fun setupEventObservers() {
        viewModel.startSearchEvent
            .onEach { hideKeyboard() }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorEvent
            .onEach(::handleError)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleError(error: ErrorEntity) {
        when (error) {
            ErrorEntity.Network -> R.string.weather_error_network
            ErrorEntity.LimitExceeded -> R.string.weather_error_limit_exceeded
            ErrorEntity.AccessDenied -> R.string.weather_error_access_denied
            ErrorEntity.NotFound -> R.string.weather_error_not_found
            else -> R.string.weather_error_unknown
        }.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun handleRequestPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            //TODO: proceed to request location
        } else {
            //TODO: show rationale dialog
        }
    }
}
