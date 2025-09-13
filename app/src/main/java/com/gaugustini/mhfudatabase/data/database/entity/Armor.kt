package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "armor",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = ArmorSetEntity::class,
            parentColumns = ["id"],
            childColumns = ["armor_set_id"],
        )
    ],
    indices = [
        Index(value = ["armor_set_id"])
    ]
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

@Entity(
    tableName = "armor_text",
    primaryKeys = ["armor_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = ArmorEntity::class,
            parentColumns = ["id"],
            childColumns = ["armor_id"]
        )
    ]
)
data class ArmorTextEntity(
    @ColumnInfo(name = "armor_id") val armorId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
)

@Entity(
    tableName = "armor_skill",
    primaryKeys = ["armor_id", "skill_tree_id"],
    foreignKeys = [
        ForeignKey(
            entity = ArmorEntity::class,
            parentColumns = ["id"],
            childColumns = ["armor_id"]
        ),
        ForeignKey(
            entity = SkillTreeEntity::class,
            parentColumns = ["id"],
            childColumns = ["skill_tree_id"]
        )
    ],
    indices = [
        Index(value = ["skill_tree_id"])
    ]
)
data class ArmorSkillEntity(
    @ColumnInfo(name = "armor_id") val armorId: Int,
    @ColumnInfo(name = "skill_tree_id") val skillTreeId: Int,
    @ColumnInfo(name = "point_value") val pointValue: Int,
)

@Entity(
    tableName = "armor_recipe",
    primaryKeys = ["armor_id", "item_id"],
    foreignKeys = [
        ForeignKey(
            entity = ArmorEntity::class,
            parentColumns = ["id"],
            childColumns = ["armor_id"]
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
data class ArmorRecipeEntity(
    @ColumnInfo(name = "armor_id") val armorId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
)
