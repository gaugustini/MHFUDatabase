package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationEntity
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationTextEntity

/**
 * Represents a location where an item can be obtained.
 */
data class LocationSource(
    @Embedded
    val locationItem: LocationItemEntity,
    @Embedded
    val location: LocationEntity,
    @Embedded
    val locationText: LocationTextEntity,
)
