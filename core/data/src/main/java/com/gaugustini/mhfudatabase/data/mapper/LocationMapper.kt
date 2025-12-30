package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText
import com.gaugustini.mhfudatabase.domain.model.Location

/**
 * Mapper for Location entities.
 */
object LocationMapper {

    fun map(
        location: LocationWithText,
    ): Location {
        return Location(
            id = location.location.id,
            name = location.locationText.name,
            items = emptyMap(),
        )
    }

    fun mapList(
        locations: List<LocationWithText>,
    ): List<Location> {
        return locations.map { map(it) }
    }

}
