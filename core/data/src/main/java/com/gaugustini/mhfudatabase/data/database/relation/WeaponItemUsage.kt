package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponTextEntity

/**
 * Represents a quantity of an item used to craft or upgrade a weapon.
 */
data class WeaponItemUsage(
    @Embedded
    val weapon: WeaponEntity,
    @Embedded
    val weaponText: WeaponTextEntity,
    val quantity: Int,
)
