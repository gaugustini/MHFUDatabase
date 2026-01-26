package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.domain.enums.SkillCategory
import com.gaugustini.mhfudatabase.domain.model.Skill
import com.gaugustini.mhfudatabase.domain.model.SkillPoint
import com.gaugustini.mhfudatabase.domain.model.SkillTree

object PreviewSkillData {

    // Skill

    val skill = Skill(
        id = 1,
        skillTreeId = 1,
        name = "Skill",
        description = "Skill Description",
        requiredPoints = 10,
    )

    val skillList = listOf(
        skill.copy(id = 1, name = "Skill 1", requiredPoints = 10),
        skill.copy(id = 2, name = "Skill 2", requiredPoints = 15),
        skill.copy(id = 3, name = "Skill 3", requiredPoints = 20),
    )

    // Skill Tree

    val skillTree = SkillTree(
        id = 1,
        name = "Skill Tree",
        category = SkillCategory.COMBAT,
        skills = null,
    )

    val skillTreeList = listOf(
        skillTree.copy(id = 1, name = "Skill Tree 1"),
        skillTree.copy(id = 2, name = "Skill Tree 2"),
        skillTree.copy(id = 3, name = "Skill Tree 3"),
    )

    // Skill Tree Points

    val skillTreePoints = SkillPoint(
        skillTree = skillTree,
        points = 5,
    )

    val skillTreePointsList = listOf(
        skillTreePoints.copy(points = -5),
        skillTreePoints.copy(points = 1),
        skillTreePoints.copy(points = 5),
    )

}
