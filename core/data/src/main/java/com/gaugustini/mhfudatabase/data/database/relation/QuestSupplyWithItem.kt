package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.quest.QuestSupplyEntity

/**
 * Represents a quest supply entity with its associated item and text entities.
 */
data class QuestSupplyWithItem(
    @Embedded(prefix = "qs_") val questSupply: QuestSupplyEntity,
    @Embedded val item: ItemEntity,
    @Embedded val itemText: ItemTextEntity,
)
