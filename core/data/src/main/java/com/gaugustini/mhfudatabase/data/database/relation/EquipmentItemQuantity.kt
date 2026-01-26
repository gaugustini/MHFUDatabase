package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity

/**
 * Represents the relationship between an equipment and the quantity of an item required to
 * craft or upgrade it.
 */
data class EquipmentItemQuantity(
    val equipmentId: Int,
    @Embedded val item: ItemEntity,
    @Embedded val itemText: ItemTextEntity,
    val quantity: Int,
)
