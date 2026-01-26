package com.gaugustini.mhfudatabase.data.database.entity.monster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "monster_hitzone",
    primaryKeys = ["monster_id", "hitzone_id"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"],
        ),
        ForeignKey(
            entity = HitzoneEntity::class,
            parentColumns = ["id"],
            childColumns = ["hitzone_id"],
        ),
    ],
    indices = [
        Index(value = ["hitzone_id"]),
    ],
)
data class MonsterHitzoneEntity(
    @ColumnInfo(name = "monster_id") val monsterId: Int,
    @ColumnInfo(name = "hitzone_id") val hitzoneId: Int,
    @ColumnInfo(name = "cut") val cut: Int,
    @ColumnInfo(name = "impact") val impact: Int,
    @ColumnInfo(name = "shot") val shot: Int,
    @ColumnInfo(name = "fire") val fire: Int,
    @ColumnInfo(name = "water") val water: Int,
    @ColumnInfo(name = "thunder") val thunder: Int,
    @ColumnInfo(name = "ice") val ice: Int,
    @ColumnInfo(name = "dragon") val dragon: Int,
)
