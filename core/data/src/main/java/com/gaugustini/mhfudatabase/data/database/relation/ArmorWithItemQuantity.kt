package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorTextEntity

/**
 * Represents an armor entity with its associated text and a quantity of a item used to craft it.
 */
data class ArmorWithItemQuantity(
    @Embedded val armor: ArmorEntity,
    @Embedded val armorText: ArmorTextEntity,
    val quantity: Int,
)
