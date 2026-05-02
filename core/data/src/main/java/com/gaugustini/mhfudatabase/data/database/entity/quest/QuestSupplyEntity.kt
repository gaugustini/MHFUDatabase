package com.gaugustini.mhfudatabase.data.database.entity.quest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity

@Entity(
    tableName = "quest_supply",
    primaryKeys = ["quest_id", "item_id", "order"],
    foreignKeys = [
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"],
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_id"],
        ),
    ],
    indices = [
        Index(value = ["item_id"]),
    ],
)
data class QuestSupplyEntity(
    @ColumnInfo(name = "quest_id") val questId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "order") val order: Int,
)
