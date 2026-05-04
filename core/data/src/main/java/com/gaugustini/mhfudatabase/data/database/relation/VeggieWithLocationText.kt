package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.veggie.VeggieEntity

/**
 * Represents a Veggie Elder entity with its associated location text.
 */
data class VeggieWithLocationText(
    @Embedded(prefix = "veggie_") val veggie: VeggieEntity,
    @Embedded val locationText: LocationTextEntity,
)
