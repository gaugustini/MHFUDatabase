package com.gaugustini.mhfudatabase.data.database.entity.weapon

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "weapon",
    primaryKeys = ["id"],
)
data class WeaponEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "weapon_type") val weaponType: String,
    @ColumnInfo(name = "rarity") val rarity: Int,
    @ColumnInfo(name = "affinity") val affinity: Int,
    @ColumnInfo(name = "defense") val defense: Int,
    @ColumnInfo(name = "num_slots") val numberOfSlots: Int,
    @ColumnInfo(name = "attack") val attack: Int,
    @ColumnInfo(name = "max_attack") val maxAttack: Int?,
    @ColumnInfo(name = "price_create") val priceCreate: Int?,
    @ColumnInfo(name = "price_upgrade") val priceUpgrade: Int?,
    @ColumnInfo(name = "element_1") val element1: String?,
    @ColumnInfo(name = "element_1_value") val element1Value: Int?,
    @ColumnInfo(name = "element_2") val element2: String?,
    @ColumnInfo(name = "element_2_value") val element2Value: Int?,
    @ColumnInfo(name = "sharpness") val sharpness: String?,
    @ColumnInfo(name = "sharpness_plus") val sharpnessPlus: String?,
    @ColumnInfo(name = "shelling_type") val shellingType: String?,
    @ColumnInfo(name = "shelling_level") val shellingLevel: Int?,
    @ColumnInfo(name = "song_notes") val songNotes: String?,
    @ColumnInfo(name = "reload_speed") val reloadSpeed: String?,
    @ColumnInfo(name = "recoil") val recoil: String?,
)
