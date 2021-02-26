package app.ch.data.location.mapper

import app.ch.data.location.model.LocationModel
import app.ch.domain.location.entity.LocationEntity

fun LocationModel.toDomainEntity(): LocationEntity {
    return LocationEntity(
        lat,
        long,
    )
}
