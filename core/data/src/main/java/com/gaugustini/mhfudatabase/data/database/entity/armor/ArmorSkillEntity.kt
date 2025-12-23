package com.gaugustini.mhfudatabase.data.database.entity.armor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeEntity

@Entity(
    tableName = "armor_skill",
    primaryKeys = ["armor_id", "skill_tree_id"],
    foreignKeys = [
        ForeignKey(
            entity = ArmorEntity::class,
            parentColumns = ["id"],
            childColumns = ["armor_id"],
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
data class ArmorSkillEntity(
    @ColumnInfo(name = "armor_id") val armorId: Int,
    @ColumnInfo(name = "skill_tree_id") val skillTreeId: Int,
    @ColumnInfo(name = "point_value") val pointValue: Int,
)
