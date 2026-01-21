package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.decoration.DecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity

/**
 * Represents a quantity of an item used to craft a decoration.
 */
data class DecorationItemUsage(
    @Embedded
    val decoration: DecorationEntity,
    @Embedded
    val item: ItemEntity,
    @Embedded
    val itemText: ItemTextEntity,
    val quantity: Int,
)
