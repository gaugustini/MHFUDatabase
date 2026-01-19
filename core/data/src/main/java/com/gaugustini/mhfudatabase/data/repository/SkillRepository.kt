package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import com.gaugustini.mhfudatabase.data.mapper.SkillMapper
import com.gaugustini.mhfudatabase.domain.model.SkillTree
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Skill.
 */
@Singleton
class SkillRepository @Inject constructor(
    private val skillDao: SkillDao,
) {

    /**
     * Returns the skill tree with the given ID.
     */
    suspend fun getSkillTree(
        skillTreeId: Int,
        language: String,
    ): SkillTree {
        return SkillMapper.toModel(
            skillTree = skillDao.getSkillTree(skillTreeId, language),
            skills = skillDao.getSkillListBySkillTreeId(skillTreeId, language),
        )
    }

    /**
     * Returns the list of all skill trees.
     */
    suspend fun getSkillTreeList(
        language: String,
    ): List<SkillTree> {
        val skillTreesWithText = skillDao.getSkillTreeList(language)
        val skillsGroupedBySkillTree = skillDao.getSkillList(language).groupBy { it.skill.skillTreeId }

        return skillTreesWithText.map {
            SkillMapper.toModel(
                skillTree = it,
                skills = skillsGroupedBySkillTree[it.skillTree.id] ?: emptyList(),
            )
        }
    }

}
