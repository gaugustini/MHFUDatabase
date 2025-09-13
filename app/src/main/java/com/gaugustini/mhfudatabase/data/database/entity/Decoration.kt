package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "decoration",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"]
        )
    ]
)
data class DecorationEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "required_slots") val requiredSlots: Int,
)

@Entity(
    tableName = "decoration_skill",
    primaryKeys = ["decoration_id", "skill_tree_id"],
    foreignKeys = [
        ForeignKey(
            entity = DecorationEntity::class,
            parentColumns = ["id"],
            childColumns = ["decoration_id"]
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
data class DecorationSkillEntity(
    @ColumnInfo(name = "decoration_id") val decorationId: Int,
    @ColumnInfo(name = "skill_tree_id") val skillTreeId: Int,
    @ColumnInfo(name = "point_value") val pointValue: Int,
)

@Entity(
    tableName = "decoration_recipe",
    primaryKeys = ["decoration_id", "item_id", "recipe_variant"],
    foreignKeys = [
        ForeignKey(
            entity = DecorationEntity::class,
            parentColumns = ["id"],
            childColumns = ["decoration_id"]
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
data class DecorationRecipeEntity(
    @ColumnInfo(name = "decoration_id") val decorationId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "recipe_variant") val variant: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
)
