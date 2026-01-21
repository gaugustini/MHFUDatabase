package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.SkillWithText
import com.gaugustini.mhfudatabase.domain.model.Skill

/**
 * Mapper for Skill entities.
 */
object SkillMapper {

    fun toModel(
        skill: SkillWithText,
    ): Skill {
        return Skill(
            id = skill.skill.id,
            skillTreeId = skill.skill.skillTreeId,
            name = skill.skillText.name,
            description = skill.skillText.description,
            requiredPoints = skill.skill.requiredPoints,
        )
    }

}
