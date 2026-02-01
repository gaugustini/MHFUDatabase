package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeTextEntity

/**
 * Represents the relationship between an armor and the points of a skill tree present in it.
 */
data class ArmorWithSkillTreePoint(
    @Embedded val armor: ArmorEntity,
    @Embedded val armorText: ArmorTextEntity,
    @Embedded(prefix = "sk_") val skillTree: SkillTreeEntity,
    @Embedded(prefix = "sktxt_") val skillTreeText: SkillTreeTextEntity,
    val points: Int,
)
