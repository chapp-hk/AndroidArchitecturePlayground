package app.ch.weatherapp.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.ch.domain.base.ErrorEntity
import app.ch.domain.base.IErrorHandler
import app.ch.domain.location.usecase.GetCurrentLocationUseCase
import app.ch.domain.weather.entity.WeatherEntity
import app.ch.domain.weather.usecase.GetLatestSearchedWeatherUseCase
import app.ch.domain.weather.usecase.GetWeatherByCityNameUseCase
import app.ch.domain.weather.usecase.GetWeatherByLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject
constructor(
    private val getWeatherByCityName: GetWeatherByCityNameUseCase,
    private val getWeatherByLocation: GetWeatherByLocationUseCase,
    private val getCurrentLocation: GetCurrentLocationUseCase,
    private val getLatestSearchedWeather: GetLatestSearchedWeatherUseCase,
    private val getErrorEntity: IErrorHandler,
) : ViewModel() {

    val searchText = MutableLiveData("")

    private val _cityName = MutableStateFlow("")
    val cityName = _cityName.asLiveData()

    private val _temperature = MutableStateFlow<Double?>(null)
    val temperature = _temperature.map { it?.toString() }.asLiveData()

    private val _feelsLike = MutableStateFlow<Double?>(null)
    val feelsLike = _feelsLike.map { it?.toString() }.asLiveData()

    private val _temperatureMin = MutableStateFlow<Double?>(null)
    val temperatureMin = _temperatureMin.map { it?.toString() }.asLiveData()

    private val _temperatureMax = MutableStateFlow<Double?>(null)
    val temperatureMax = _temperatureMax.map { it?.toString() }.asLiveData()

    private val _pressure = MutableStateFlow<Int?>(null)
    val pressure = _pressure.map { it?.toString() }.asLiveData()

    private val _humidity = MutableStateFlow<Int?>(null)
    val humidity = _humidity.map { it?.toString() }.asLiveData()

    private val _visibility = MutableStateFlow<Int?>(null)
    val visibility = _visibility.map { it?.toString() }.asLiveData()

    private val _windSpeed = MutableStateFlow<Double?>(null)
    val windSpeed = _windSpeed.map { it?.toString() }.asLiveData()

    private val _windDeg = MutableStateFlow<Int?>(null)
    val windDeg = _windDeg.map { it?.toString() }.asLiveData()

    private val _cloudiness = MutableStateFlow<Int?>(null)
    val cloudiness = _cloudiness.map { it?.toString() }.asLiveData()

    private val _isEmptyHistory = MutableStateFlow<Boolean?>(null)
    val isEmptyHistory = _isEmptyHistory.asLiveData()
    val isLoaded = _isEmptyHistory.map { it?.not() }.asLiveData()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asLiveData()

    private val _weatherEvent = MutableSharedFlow<WeatherEvent>()
    val weatherEvent = _weatherEvent.asSharedFlow()

    fun queryLatestSearchedWeather() {
        getLatestSearchedWeather()
            .run(::startFlow)
            .launchIn(viewModelScope)
    }

    fun queryWeatherByCityName(cityName: String? = null) {
        getWeatherByCityName(cityName ?: searchText.value.orEmpty())
            .run(::startFlow)
            .launchIn(viewModelScope)
    }

    @ExperimentalCoroutinesApi
    fun queryCurrentLocation() {
        getCurrentLocation()
            .flatMapLatest { getWeatherByLocation(it.lat, it.long) }
            .run(::startFlow)
            .launchIn(viewModelScope)
    }

    private fun startFlow(flow: Flow<WeatherEntity>): Flow<WeatherEntity> {
        return flow.onStart {
            _weatherEvent.emit(WeatherEvent.StartSearch)
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
        }.catch {
            Timber.e(it)
            handleError(it)
        }.onEach {
            _cityName.value = it.name
            _temperature.value = it.temperature
            _feelsLike.value = it.feelsLike
            _temperatureMin.value = it.temperatureMin
            _temperatureMax.value = it.temperatureMax
            _pressure.value = it.pressure
            _humidity.value = it.humidity
            _visibility.value = it.visibility
            _windSpeed.value = it.windSpeed
            _windDeg.value = it.windDeg
            _cloudiness.value = it.cloudiness
            _isEmptyHistory.value = false
        }
    }

    private suspend fun handleError(throwable: Throwable) {
        when (val error = getErrorEntity(throwable)) {
            is ErrorEntity.EmptyHistory -> _isEmptyHistory.value = true
            else -> _weatherEvent.emit(WeatherEvent.Error(error))
        }
    }
}
