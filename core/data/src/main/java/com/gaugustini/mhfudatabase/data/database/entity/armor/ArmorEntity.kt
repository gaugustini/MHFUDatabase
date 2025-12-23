package com.gaugustini.mhfudatabase.data.database.entity.armor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.armorset.ArmorSetEntity

@Entity(
    tableName = "armor",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = ArmorSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["armor_set_id"],
        ),
    ],
    indices = [
        Index(value = ["armor_set_id"]),
    ],
)
data class ArmorEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "armor_set_id") val armorSetId: Int,
    @ColumnInfo(name = "armor_type") val armorType: String,
    @ColumnInfo(name = "hunter_type") val hunterType: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "rarity") val rarity: Int,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "num_slots") val numberOfSlots: Int,
    @ColumnInfo(name = "defense") val defense: Int,
    @ColumnInfo(name = "max_defense") val maxDefense: Int,
    @ColumnInfo(name = "fire_res") val fireResistance: Int,
    @ColumnInfo(name = "water_res") val waterResistance: Int,
    @ColumnInfo(name = "thunder_res") val thunderResistance: Int,
    @ColumnInfo(name = "ice_res") val iceResistance: Int,
    @ColumnInfo(name = "dragon_res") val dragonResistance: Int,
)
