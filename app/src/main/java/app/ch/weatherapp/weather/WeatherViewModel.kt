package app.ch.weatherapp.weather

import androidx.lifecycle.*
import app.ch.domain.base.ErrorEntity
import app.ch.domain.base.IErrorHandler
import app.ch.domain.weather.usecase.GetWeatherByCityNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class WeatherViewModel @Inject
constructor(
    private val getWeatherByCityName: GetWeatherByCityNameUseCase,
    private val handleError: IErrorHandler,
) : ViewModel() {

    val searchText = MutableLiveData("")

    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading.asFlow().asLiveData()

    private val _cityName = MutableLiveData("")
    val cityName = _cityName.asFlow().asLiveData()

    private val _temperature = MutableLiveData<Double>()
    val temperature = Transformations.map(_temperature) { it.toString() }

    private val _feelsLike = MutableLiveData<Double>()
    val feelsLike = Transformations.map(_feelsLike) { it.toString() }

    private val _temperatureMin = MutableLiveData<Double>()
    val temperatureMin = Transformations.map(_temperatureMin) { it.toString() }

    private val _temperatureMax = MutableLiveData<Double>()
    val temperatureMax = Transformations.map(_temperatureMax) { it.toString() }

    private val _pressure = MutableLiveData<Int>()
    val pressure = Transformations.map(_pressure) { it.toString() }

    private val _humidity = MutableLiveData<Int>()
    val humidity = Transformations.map(_humidity) { it.toString() }

    private val _visibility = MutableLiveData<Int>()
    val visibility = Transformations.map(_visibility) { it.toString() }

    private val _windSpeed = MutableLiveData<Double>()
    val windSpeed = Transformations.map(_windSpeed) { it.toString() }

    private val _windDeg = MutableLiveData<Int>()
    val windDeg = Transformations.map(_windDeg) { it.toString() }

    private val _cloudiness = MutableLiveData<Int>()
    val cloudiness = Transformations.map(_cloudiness) { it.toString() }

    private val _startSearchEvent = MutableSharedFlow<Unit>()
    val startSearchEvent = _startSearchEvent.asSharedFlow()

    private val _errorEvent = MutableSharedFlow<ErrorEntity>()
    val errorEvent = _errorEvent.asSharedFlow()

    fun queryWeatherByCityName() {
        viewModelScope.launch {
            getWeatherByCityName(searchText.value.orEmpty())
                .onStart {
                    _startSearchEvent.emit(Unit)
                    _isLoading.value = true
                }
                .onCompletion {
                    _isLoading.value = false
                }
                .catch { throwable ->
                    Timber.e(throwable)
                    _errorEvent.emit(handleError(throwable))
                }
                .collectLatest {
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
                }
        }
    }
}
