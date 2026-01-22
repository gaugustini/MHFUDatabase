package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationItemEntity

/**
 * Represents a location item and its associated item and text entities.
 */
data class LocationItem(
    @Embedded
    val locationItem: LocationItemEntity,
    @Embedded
    val item: ItemEntity,
    @Embedded
    val itemText: ItemTextEntity,
)
