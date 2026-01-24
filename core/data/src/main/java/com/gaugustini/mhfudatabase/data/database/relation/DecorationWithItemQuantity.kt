package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.decoration.DecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity

/**
 * Represents a decoration entity with its associated item and text values, and a quantity of a item
 * used to craft it.
 */
data class DecorationWithItemQuantity(
    @Embedded val decoration: DecorationEntity,
    @Embedded val item: ItemEntity,
    @Embedded val itemText: ItemTextEntity,
    val quantity: Int,
)
