package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTextEntity

/**
 * Represents a skill entity with its associated text.
 */
data class SkillWithText(
    @Embedded val skill: SkillEntity,
    @Embedded val skillText: SkillTextEntity,
)
