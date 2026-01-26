package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeTextEntity

/**
 * Represents a skill tree entity with its associated text.
 */
data class SkillTreeWithText(
    @Embedded val skillTree: SkillTreeEntity,
    @Embedded val skillTreeText: SkillTreeTextEntity,
)
