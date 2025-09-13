package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "weapon",
    primaryKeys = ["id"]
)
data class WeaponEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "weapon_type") val weaponType: String,
    @ColumnInfo(name = "rarity") val rarity: Int,
    @ColumnInfo(name = "affinity") val affinity: Int,
    @ColumnInfo(name = "defense") val defense: Int,
    @ColumnInfo(name = "num_slots") val numSlots: Int,
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

@Entity(
    tableName = "weapon_text",
    primaryKeys = ["weapon_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"]
        )
    ]
)
data class WeaponTextEntity(
    @ColumnInfo(name = "weapon_id") val weaponId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
)

@Entity(
    tableName = "weapon_parent",
    primaryKeys = ["weapon_id", "parent_weapon_id"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"]
        ),
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["parent_weapon_id"]
        )
    ],
    indices = [
        Index(value = ["parent_weapon_id"])
    ]
)
data class WeaponParentEntity(
    @ColumnInfo(name = "weapon_id") val weaponId: Int,
    @ColumnInfo(name = "parent_weapon_id") val parentWeaponId: Int,
)

@Entity(
    tableName = "weapon_recipe",
    primaryKeys = ["weapon_id", "item_id", "recipe_type"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"]
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_id"]
        )
    ],
    indices = [
        Index(value = ["item_id"])
    ]
)
data class WeaponRecipeEntity(
    @ColumnInfo(name = "weapon_id") val weaponId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "recipe_type") val recipeType: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
)

@Entity(
    tableName = "weapon_ammo_bow",
    primaryKeys = ["weapon_id"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"]
        )
    ]
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

@Entity(
    tableName = "weapon_ammo_bowgun",
    primaryKeys = ["weapon_id"],
    foreignKeys = [
        ForeignKey(
            entity = WeaponEntity::class,
            parentColumns = ["id"],
            childColumns = ["weapon_id"]
        )
    ]
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
