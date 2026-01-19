package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.armorset.ArmorSetEntity
import com.gaugustini.mhfudatabase.data.database.entity.armorset.ArmorSetTextEntity

/**
 * Represents an armor set entity with its associated text.
 */
data class ArmorSetWithText(
    @Embedded
    val armorSet: ArmorSetEntity,
    @Embedded
    val armorSetText: ArmorSetTextEntity,
    val defense: Int,
    val maxDefense: Int,
    val fire: Int,
    val water: Int,
    val thunder: Int,
    val ice: Int,
    val dragon: Int,
)
