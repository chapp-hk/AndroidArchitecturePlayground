package app.ch.mobile.service.gms.location


import android.location.Location
import app.ch.data.location.model.LocationModel

fun Location.toDataModel(): LocationModel {
    return LocationModel(
        latitude,
        longitude,
    )
}
