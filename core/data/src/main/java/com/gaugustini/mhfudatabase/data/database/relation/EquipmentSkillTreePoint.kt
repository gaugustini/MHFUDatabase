package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeTextEntity

/**
 * Represents the relationship between an equipment and the points of a skill tree present in it.
 */
data class EquipmentSkillTreePoint(
    val equipmentId: Int,
    @Embedded val skillTree: SkillTreeEntity,
    @Embedded val skillTreeText: SkillTreeTextEntity,
    val points: Int,
)
