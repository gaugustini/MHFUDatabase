package com.gaugustini.mhfudatabase.data.database.entity.skill

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "skill_tree_text",
    primaryKeys = ["skill_tree_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = SkillTreeEntity::class,
            parentColumns = ["id"],
            childColumns = ["skill_tree_id"],
        ),
    ],
)
data class SkillTreeTextEntity(
    @ColumnInfo(name = "skill_tree_id") val skillTreeId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
)
