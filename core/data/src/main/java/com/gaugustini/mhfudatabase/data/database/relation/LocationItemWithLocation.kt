package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationEntity
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationTextEntity

/**
 * Represents a location item entity and its associated location and text entities.
 */
data class LocationItemWithLocation(
    @Embedded(prefix = "li_") val locationItem: LocationItemEntity,
    @Embedded val location: LocationEntity,
    @Embedded val locationText: LocationTextEntity,
)
