package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "skill",
    primaryKeys = ["id"],
    foreignKeys = [
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
data class SkillEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "skill_tree_id") val skillTreeId: Int,
    @ColumnInfo(name = "required_points") val requiredPoints: Int,
)

@Entity(
    tableName = "skill_text",
    primaryKeys = ["skill_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = SkillEntity::class,
            parentColumns = ["id"],
            childColumns = ["skill_id"]
        )
    ]
)
data class SkillTextEntity(
    @ColumnInfo(name = "skill_id") val skillTreeId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "description") val description: String,
)
