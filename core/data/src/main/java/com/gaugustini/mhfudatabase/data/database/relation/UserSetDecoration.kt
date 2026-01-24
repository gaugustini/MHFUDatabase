package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.decoration.DecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetDecorationEntity

/**
 * Represents a decoration inserted in the equipment set.
 */
data class UserSetDecoration(
    @Embedded
    val userSetDecoration: UserEquipmentSetDecorationEntity,
    @Embedded
    val decoration: DecorationEntity,
    @Embedded
    val item: ItemEntity,
    @Embedded
    val itemText: ItemTextEntity,
)
