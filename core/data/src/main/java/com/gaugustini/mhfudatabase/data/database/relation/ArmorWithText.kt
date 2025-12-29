package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorTextEntity

/**
 * Represents an armor entity with its associated text.
 */
data class ArmorWithText(
    @Embedded
    val armor: ArmorEntity,
    @Embedded
    val armorText: ArmorTextEntity,
)
