package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity

/**
 * Represents an item entity with its associated text.
 */
data class ItemWithText(
    @Embedded val item: ItemEntity,
    @Embedded val itemText: ItemTextEntity,
)
