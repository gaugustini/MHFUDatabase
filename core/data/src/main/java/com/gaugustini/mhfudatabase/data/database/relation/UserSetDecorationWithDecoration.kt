package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.decoration.DecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetDecorationEntity

/**
 * Represents a user set decoration entity with its associated decoration, item, and text entities.
 */
data class UserSetDecorationWithDecoration(
    @Embedded val userSetDecoration: UserEquipmentSetDecorationEntity,
    @Embedded val decoration: DecorationEntity,
    @Embedded val item: ItemEntity,
    @Embedded val itemText: ItemTextEntity,
)
