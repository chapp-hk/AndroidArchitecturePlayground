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
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import app.ch.base.getBinding
import app.ch.base.hideKeyboard
import app.ch.base.recyclerview.recyclerViewAdapter
import app.ch.base.showSnackBar
import app.ch.domain.base.ErrorEntity
import app.ch.weatherapp.BR
import app.ch.weatherapp.R
import app.ch.weatherapp.databinding.FragmentWeatherBinding
import app.ch.weatherapp.history.KEY_CITY_NAME
import app.ch.weatherapp.history.REQUEST_DISPLAY_CITY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val viewModel by viewModels<WeatherViewModel>()
    private val adapter by recyclerViewAdapter<WeatherConditionListItem>(BR.listItem)

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
            it.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return viewModel.queryWeatherByCityName(query).let { true }
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
            it.btnLocation.setOnClickListener {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            it.recyclerView.adapter = adapter
        }
    }

    private fun setupEventObservers() {
        viewModel.weatherEvent
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { handleEvent(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.conditions
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { adapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        setFragmentResultListener(REQUEST_DISPLAY_CITY) { requestKey, data ->
            when (requestKey) {
                REQUEST_DISPLAY_CITY ->
                    viewModel.queryWeatherByCityName(data.getString(KEY_CITY_NAME, ""))
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
            ErrorEntity.Network -> getBinding<FragmentWeatherBinding>().containerWeather
                .showSnackBar(R.string.weather_error_network)

            ErrorEntity.LimitExceeded -> getBinding<FragmentWeatherBinding>().containerWeather
                .showSnackBar(R.string.weather_error_limit_exceeded)

            ErrorEntity.AccessDenied -> getBinding<FragmentWeatherBinding>().containerWeather
                .showSnackBar(R.string.weather_error_access_denied)

            ErrorEntity.NotFound -> getBinding<FragmentWeatherBinding>().containerWeather
                .showSnackBar(R.string.weather_error_not_found)

            ErrorEntity.LocationUnavailable -> getBinding<FragmentWeatherBinding>().containerWeather
                .showSnackBar(
                    messageResId = R.string.weather_error_enable_location,
                    actionResId = R.string.weather_button_setting,
                ) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

            else -> getBinding<FragmentWeatherBinding>().containerWeather
                .showSnackBar(R.string.weather_error_unknown)
        }
    }

    private fun handleRequestPermissionResult(isGranted: Boolean) {
        if (isGranted) {
            viewModel.queryWeatherByLocation()
        } else {
            getBinding<FragmentWeatherBinding>().containerWeather.showSnackBar(
                messageResId = R.string.weather_error_location_permission_required,
                actionResId = R.string.weather_button_setting,
            ) {
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(
                        Uri.fromParts("package", requireContext().packageName, null)
                    )
                    .let { startActivity(it) }
            }
        }
    }
}
