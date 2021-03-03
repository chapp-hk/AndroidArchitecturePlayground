package app.ch.weatherapp.weather

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import app.ch.base.hideKeyboard
import app.ch.base.showSnackBar
import app.ch.domain.base.ErrorEntity
import app.ch.weatherapp.R
import app.ch.weatherapp.databinding.FragmentWeatherBinding
import app.ch.weatherapp.history.KEY_CITY_NAME
import app.ch.weatherapp.history.REQUEST_DISPLAY_CITY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val viewModel by viewModels<WeatherViewModel>()

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::handleRequestPermissionResult
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.queryLatestSearchedWeather()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupViews(view)
        setupEventObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

            _binding = it
        }
    }

    private fun setupEventObservers() {
        viewModel.weatherEvent
            .onEach { handleEvent(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        setFragmentResultListener(REQUEST_DISPLAY_CITY) { requestKey, data ->
            when (requestKey) {
                REQUEST_DISPLAY_CITY ->
                    viewModel.queryWeatherByCityName(data.getString(KEY_CITY_NAME))
            }
        }
    }

    private fun handleEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.StartSearch -> hideKeyboard()
            is WeatherEvent.Error -> handleError(event.error)
        }
    }

    private fun handleError(error: ErrorEntity) {
        when (error) {
            ErrorEntity.Network -> showSnackBar(
                binding.containerWeather,
                R.string.weather_error_network
            )

            ErrorEntity.LimitExceeded -> showSnackBar(
                binding.containerWeather,
                R.string.weather_error_limit_exceeded
            )

            ErrorEntity.AccessDenied -> showSnackBar(
                binding.containerWeather,
                R.string.weather_error_access_denied
            )

            ErrorEntity.NotFound -> showSnackBar(
                binding.containerWeather,
                R.string.weather_error_not_found
            )

            ErrorEntity.LocationUnavailable -> showSnackBar(
                view = binding.containerWeather,
                messageResId = R.string.weather_error_enable_location,
                actionResId = R.string.weather_button_setting,
            ) {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }

            else -> showSnackBar(
                binding.containerWeather,
                R.string.weather_error_unknown
            )
        }
    }

    private fun handleRequestPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            viewModel.queryCurrentLocation()
        } else {
            showSnackBar(
                view = binding.containerWeather,
                messageResId = R.string.weather_error_location_permission_required,
                actionResId = R.string.weather_button_setting,
            ) {
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }.let {
                    startActivity(it)
                }
            }
        }
    }
}
