package app.ch.weatherapp.weather

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import app.ch.base.hideKeyboard
import app.ch.base.showToast
import app.ch.domain.base.ErrorEntity
import app.ch.weatherapp.R
import app.ch.weatherapp.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

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
        setHasOptionsMenu(true)
        setupViews(view)
        setupEventObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.weather_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun setupViews(view: View) {
        FragmentWeatherBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
            it.etSearch.setOnEditorActionListener { _, _, _ ->
                viewModel.queryWeatherByCityName().let { true }
            }
            it.btnLocation.setOnClickListener {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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
            ErrorEntity.Network -> showToast(R.string.weather_error_network)
            ErrorEntity.LimitExceeded -> showToast(R.string.weather_error_limit_exceeded)
            ErrorEntity.AccessDenied -> showToast(R.string.weather_error_access_denied)
            ErrorEntity.NotFound -> showToast(R.string.weather_error_not_found)
            ErrorEntity.LocationUnavailable -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            else -> showToast(R.string.weather_error_unknown)
        }
    }

    private fun handleRequestPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            viewModel.queryCurrentLocation()
        } else {
            //TODO: show rationale dialog
            Timber.d("show rationale dialog")
        }
    }
}
