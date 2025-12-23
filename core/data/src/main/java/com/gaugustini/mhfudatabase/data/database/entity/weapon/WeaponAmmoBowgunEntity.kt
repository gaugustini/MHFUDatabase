package com.gaugustini.mhfudatabase.data.database.entity.weapon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "weapon_ammo_bowgun",
    primaryKeys = ["weapon_id"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"],
        ),
    ],
)
data class WeaponAmmoBowgunEntity(
    @ColumnInfo(name = "weapon_id") val weaponId: Int,
    @ColumnInfo(name = "normal") val normal: String,
    @ColumnInfo(name = "pierce") val pierce: String,
    @ColumnInfo(name = "pellet") val pellet: String,
    @ColumnInfo(name = "crag") val crag: String,
    @ColumnInfo(name = "clust") val clust: String,
    @ColumnInfo(name = "recovery") val recovery: String,
    @ColumnInfo(name = "poison") val poison: String,
    @ColumnInfo(name = "paralysis") val paralysis: String,
    @ColumnInfo(name = "sleep") val sleep: String,
    @ColumnInfo(name = "flame") val flame: String,
    @ColumnInfo(name = "water") val water: String,
    @ColumnInfo(name = "thunder") val thunder: String,
    @ColumnInfo(name = "freeze") val freeze: String,
    @ColumnInfo(name = "dragon") val dragon: String,
    @ColumnInfo(name = "tranq") val tranq: String,
    @ColumnInfo(name = "paint") val paint: String,
    @ColumnInfo(name = "demon") val demon: String,
    @ColumnInfo(name = "armor") val armor: String,
    @ColumnInfo(name = "rapid_fire") val rapidFire: String?,
)
