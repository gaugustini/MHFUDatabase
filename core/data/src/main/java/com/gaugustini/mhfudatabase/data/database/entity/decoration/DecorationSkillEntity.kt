package com.gaugustini.mhfudatabase.data.database.entity.decoration

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeEntity

@Entity(
    tableName = "decoration_skill",
    primaryKeys = ["decoration_id", "skill_tree_id"],
    foreignKeys = [
        ForeignKey(
            entity = DecorationEntity::class,
            parentColumns = ["id"],
            childColumns = ["decoration_id"],
        ),
        ForeignKey(
            entity = SkillTreeEntity::class,
            parentColumns = ["id"],
            childColumns = ["skill_tree_id"],
        ),
    ],
    indices = [
        Index(value = ["skill_tree_id"]),
    ],
)
data class DecorationSkillEntity(
    @ColumnInfo(name = "decoration_id") val decorationId: Int,
    @ColumnInfo(name = "skill_tree_id") val skillTreeId: Int,
    @ColumnInfo(name = "point_value") val pointValue: Int,
)
