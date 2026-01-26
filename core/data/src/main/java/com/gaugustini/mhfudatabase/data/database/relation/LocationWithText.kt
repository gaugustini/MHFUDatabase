package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationEntity
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationTextEntity

/**
 * Represents a location entity with its associated text.
 */
data class LocationWithText(
    @Embedded val location: LocationEntity,
    @Embedded val locationText: LocationTextEntity,
)
