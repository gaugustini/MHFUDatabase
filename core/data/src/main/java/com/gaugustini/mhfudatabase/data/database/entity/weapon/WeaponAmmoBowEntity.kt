package com.gaugustini.mhfudatabase.data.database.entity.weapon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "weapon_ammo_bow",
    primaryKeys = ["weapon_id"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"],
        ),
    ],
)
data class WeaponAmmoBowEntity(
    @ColumnInfo(name = "weapon_id") val weaponId: Int,
    @ColumnInfo(name = "charge_1_type") val charge1Type: String,
    @ColumnInfo(name = "charge_1_level") val charge1Level: Int,
    @ColumnInfo(name = "charge_2_type") val charge2Type: String,
    @ColumnInfo(name = "charge_2_level") val charge2Level: Int,
    @ColumnInfo(name = "charge_3_type") val charge3Type: String,
    @ColumnInfo(name = "charge_3_level") val charge3Level: Int,
    @ColumnInfo(name = "charge_4_type") val charge4Type: String?,
    @ColumnInfo(name = "charge_4_level") val charge4Level: Int?,
    @ColumnInfo(name = "power") val power: Boolean,
    @ColumnInfo(name = "close") val close: Boolean,
    @ColumnInfo(name = "paint") val paint: Boolean,
    @ColumnInfo(name = "poison") val poison: Boolean,
    @ColumnInfo(name = "paralysis") val paralysis: Boolean,
    @ColumnInfo(name = "sleep") val sleep: Boolean,
)
