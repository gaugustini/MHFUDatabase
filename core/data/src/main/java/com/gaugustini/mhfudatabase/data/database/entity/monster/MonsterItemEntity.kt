package com.gaugustini.mhfudatabase.data.database.entity.monster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "monster_item",
    primaryKeys = ["monster_id", "state"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"],
        ),
    ],
)
data class MonsterItemEntity(
    @ColumnInfo(name = "monster_id") val monsterId: Int,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "pitfall") val pitfall: Boolean,
    @ColumnInfo(name = "shock") val shock: Boolean,
    @ColumnInfo(name = "flash") val flash: Boolean,
    @ColumnInfo(name = "sonic") val sonic: Boolean,
    @ColumnInfo(name = "dung") val dung: Boolean,
    @ColumnInfo(name = "meat") val meat: Boolean,
)
