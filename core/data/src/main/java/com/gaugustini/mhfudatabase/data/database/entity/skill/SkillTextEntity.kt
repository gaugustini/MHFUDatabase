package com.gaugustini.mhfudatabase.data.database.entity.skill

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "skill_text",
    primaryKeys = ["skill_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = SkillEntity::class,
            parentColumns = ["id"],
            childColumns = ["skill_id"],
        ),
    ],
)
data class SkillTextEntity(
    @ColumnInfo(name = "skill_id") val skillTreeId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "description") val description: String,
)
