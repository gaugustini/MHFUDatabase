package com.gaugustini.mhfudatabase.data.database.entity.skill

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "skill_tree",
    primaryKeys = ["id"],
)
data class SkillTreeEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "category") val category: String,
)
