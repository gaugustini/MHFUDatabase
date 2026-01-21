package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.GatheringPoint
import com.gaugustini.mhfudatabase.domain.model.Location

/**
 * Mapper for Location entities.
 */
object LocationMapper {

    fun toModel(
        location: LocationWithText,
        items: Map<Rank, List<GatheringPoint>>? = null,
    ): Location {
        return Location(
            id = location.location.id,
            name = location.locationText.name,
            items = items,
        )
    }

}
