package com.gaugustini.mhfudatabase.data.database.entity.monster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "monster_item",
    primaryKeys = ["monster_id"],
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
    @ColumnInfo(name = "flash") val flash: Boolean,
    @ColumnInfo(name = "time_flash") val timeFlash: Int?,
    @ColumnInfo(name = "sonic_normal") val sonicNormal: Boolean,
    @ColumnInfo(name = "sonic_enraged") val sonicEnraged: Boolean,
    @ColumnInfo(name = "shock") val shock: Boolean,
    @ColumnInfo(name = "time_shock") val timeShock: Int?,
    @ColumnInfo(name = "pitfall_normal") val pitfallNormal: Boolean,
    @ColumnInfo(name = "pitfall_enraged") val pitfallEnraged: Boolean,
    @ColumnInfo(name = "time_pitfall_unseen") val timePitfallUnseen: Int?,
    @ColumnInfo(name = "time_pitfall_normal") val timePitfallNormal: Int?,
    @ColumnInfo(name = "time_pitfall_enraged") val timePitfallEnraged: Int?,
    @ColumnInfo(name = "meat") val meat: Boolean,
    @ColumnInfo(name = "dung") val dung: Boolean,
)
