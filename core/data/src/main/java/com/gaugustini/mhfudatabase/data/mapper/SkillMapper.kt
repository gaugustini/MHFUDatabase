package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.SkillTreeWithText
import com.gaugustini.mhfudatabase.data.database.relation.SkillWithText
import com.gaugustini.mhfudatabase.domain.enums.SkillCategory
import com.gaugustini.mhfudatabase.domain.model.Skill
import com.gaugustini.mhfudatabase.domain.model.SkillTree

/**
 * Mapper for Skill Tree and Skill entities.
 */
object SkillMapper {

    fun toModel(
        skillTree: SkillTreeWithText,
        skills: List<Skill>,
        points: Int? = null,
    ): SkillTree {
        return SkillTree(
            id = skillTree.skillTree.id,
            name = skillTree.skillTreeText.name,
            category = SkillCategory.fromString(skillTree.skillTree.category),
            skills = skills,
            points = points,
        )
    }

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
