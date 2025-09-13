package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "skill_tree",
    primaryKeys = ["id"]
)
data class SkillTreeEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "category") val category: String,
)

@Entity(
    tableName = "skill_tree_text",
    primaryKeys = ["skill_tree_id"],
    foreignKeys = [
        ForeignKey(
            entity = SkillTreeEntity::class,
            parentColumns = ["id"],
            childColumns = ["skill_tree_id"]
        )
    ]
)
data class SkillTreeTextEntity(
    @ColumnInfo(name = "skill_tree_id") val skillTreeId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
)
