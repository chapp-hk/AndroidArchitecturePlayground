package app.ch.data.weather.mapper

import app.ch.data.weather.local.ConditionDaoEntity
import app.ch.data.weather.local.WeatherDaoEntity
import app.ch.data.weather.local.WeatherWithConditions
import app.ch.data.weather.model.WeatherModel
import app.ch.data.weather.remote.WeatherResponse
import app.ch.domain.weather.entity.WeatherEntity

/**
 * map remote response to data layer model
 */
fun WeatherResponse.toDataModel(): WeatherModel {
    return WeatherModel(
        id = this.id ?: 0,
        name = this.name.orEmpty(),
        coordLat = this.coord?.lat ?: 0.0,
        coordLon = this.coord?.lon ?: 0.0,
        conditions = this.conditions.map {
            WeatherModel.Condition(
                id = it.id,
                main = it.main,
                description = it.description,
                iconUrl = it.iconUrl,
                weatherModelId = this.id ?: 0,
            )
        },
        temperature = this.main?.temp ?: 0.0,
        feelsLike = this.main?.feelsLike ?: 0.0,
        temperatureMin = this.main?.tempMin ?: 0.0,
        temperatureMax = this.main?.tempMax ?: 0.0,
        pressure = this.main?.pressure ?: 0,
        humidity = this.main?.humidity ?: 0,
        visibility = this.visibility ?: 0,
        windSpeed = this.wind?.speed ?: 0.0,
        windDeg = this.wind?.deg ?: 0,
        cloudiness = this.clouds?.all ?: 0,
    )
}

/**
 * map local dao to data layer model
 */
fun WeatherWithConditions.toDataModel(): WeatherModel {
    return WeatherModel(
        id = this.weather.id,
        name = this.weather.name,
        coordLat = this.weather.coordLat,
        coordLon = this.weather.coordLon,
        conditions = this.conditions.map {
            WeatherModel.Condition(
                id = it.id,
                main = it.main,
                description = it.description,
                iconUrl = it.iconUrl,
                weatherModelId = this.weather.id,
            )
        },
        temperature = this.weather.temperature,
        feelsLike = this.weather.feelsLike,
        temperatureMin = this.weather.temperatureMin,
        temperatureMax = this.weather.temperatureMax,
        pressure = this.weather.pressure,
        humidity = this.weather.humidity,
        visibility = this.weather.visibility,
        windSpeed = this.weather.windSpeed,
        windDeg = this.weather.windDeg,
        cloudiness = this.weather.cloudiness,
    )
}

/**
 * map data layer model to domain layer entity
 */
fun WeatherModel.toDomainEntity(): WeatherEntity {
    return WeatherEntity(
        id = this.id,
        name = this.name,
        coordLat = this.coordLat,
        coordLon = this.coordLon,
        conditions = this.conditions.map {
            WeatherEntity.Condition(
                id = it.id,
                main = it.main,
                description = it.description,
                iconUrl = it.iconUrl,
            )
        },
        temperature = this.temperature,
        feelsLike = this.feelsLike,
        temperatureMin = this.temperatureMin,
        temperatureMax = this.temperatureMax,
        pressure = this.pressure,
        humidity = this.humidity,
        visibility = this.visibility,
        windSpeed = this.windSpeed,
        windDeg = this.windDeg,
        cloudiness = this.cloudiness,
    )
}

/**
 * map data layer model to dao entity
 */
fun WeatherModel.toDaoEntity(): WeatherDaoEntity {
    return WeatherDaoEntity(
        id = this.id,
        name = this.name,
        coordLat = this.coordLat,
        coordLon = this.coordLon,
        temperature = this.temperature,
        feelsLike = this.feelsLike,
        temperatureMin = this.temperatureMin,
        temperatureMax = this.temperatureMax,
        pressure = this.pressure,
        humidity = this.humidity,
        visibility = this.visibility,
        windSpeed = this.windSpeed,
        windDeg = this.windDeg,
        cloudiness = this.cloudiness,
    )
}

/**
 * map data layer model to dao entity
 */
fun WeatherModel.Condition.toDaoEntity(): ConditionDaoEntity {
    return ConditionDaoEntity(
        id = this.id,
        main = this.main,
        description = this.description,
        iconUrl = this.iconUrl,
        weatherId = this.weatherModelId,
    )
}
