package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorTextEntity

/**
 * Represents a quantity of an item used to craft an armor.
 */
data class ArmorItemUsage(
    @Embedded
    val armor: ArmorEntity,
    @Embedded
    val armorText: ArmorTextEntity,
    val quantity: Int,
)
