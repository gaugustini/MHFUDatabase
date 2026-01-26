package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponTextEntity

/**
 * Represents a weapon entity with its associated text and a quantity of a item used to craft it.
 */
data class WeaponWithItemQuantity(
    @Embedded val weapon: WeaponEntity,
    @Embedded val weaponText: WeaponTextEntity,
    val quantity: Int,
)
