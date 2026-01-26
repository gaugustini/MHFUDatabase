package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.EquipmentSkillTreePoint
import com.gaugustini.mhfudatabase.data.database.relation.SkillTreeWithText
import com.gaugustini.mhfudatabase.data.database.relation.SkillWithText
import com.gaugustini.mhfudatabase.domain.enums.SkillCategory
import com.gaugustini.mhfudatabase.domain.model.SkillPoint
import com.gaugustini.mhfudatabase.domain.model.SkillTree

/**
 * Mapper for Skill Tree entities.
 */
object SkillTreeMapper {

    fun toModel(
        skillTree: SkillTreeWithText,
        skills: List<SkillWithText>? = null,
    ): SkillTree {
        return SkillTree(
            id = skillTree.skillTree.id,
            name = skillTree.skillTreeText.name,
            category = SkillCategory.fromString(skillTree.skillTree.category),
            skills = skills?.map { SkillMapper.toModel(it) },
        )
    }

    fun toSkillPoint(
        equipmentSkillTreePoint: EquipmentSkillTreePoint,
    ): SkillPoint {
        return SkillPoint(
            skillTree = SkillTree(
                id = equipmentSkillTreePoint.skillTree.id,
                name = equipmentSkillTreePoint.skillTreeText.name,
                category = SkillCategory.fromString(equipmentSkillTreePoint.skillTree.category),
                skills = null,
            ),
            points = equipmentSkillTreePoint.points,
        )
    }

}
