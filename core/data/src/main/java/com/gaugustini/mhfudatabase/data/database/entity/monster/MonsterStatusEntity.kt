package com.gaugustini.mhfudatabase.data.database.entity.monster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "monster_status",
    primaryKeys = ["monster_id", "status"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"],
        ),
    ],
)
data class MonsterStatusEntity(
    @ColumnInfo(name = "monster_id") val monsterId: Int,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "initial") val initial: Int,
    @ColumnInfo(name = "increase") val increase: Int,
    @ColumnInfo(name = "max") val max: Int,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "damage") val damage: Int,
)
