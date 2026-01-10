package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import com.gaugustini.mhfudatabase.data.mapper.SkillMapper
import com.gaugustini.mhfudatabase.domain.model.Skill
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
        val skillTreeWithText = skillDao.getSkillTree(skillTreeId, language)
        val skills = getSkillsForSkillTree(skillTreeId, language)

        return SkillMapper.toModel(
            skillTree = skillTreeWithText,
            skills = skills,
        )
    }

    /**
     * Returns the list of all skill trees.
     */
    suspend fun getSkillTreeList(
        language: String,
    ): List<SkillTree> {
        val skillTreesWithText = skillDao.getSkillTreeList(language)
        val skillsGroupedBySkillTree = getSkillList(language).groupBy { it.skillTreeId }

        return skillTreesWithText.map {
            SkillMapper.toModel(
                skillTree = it,
                skills = skillsGroupedBySkillTree[it.skillTree.id] ?: emptyList(),
            )
        }
    }

    /**
     * Returns the list of all skills.
     */
    suspend fun getSkillList(
        language: String,
    ): List<Skill> {
        val skillsWithText = skillDao.getSkillList(language)

        return skillsWithText.map { SkillMapper.toModel(it) }
    }

    /**
     * Returns the list of skills of the given skill tree ID.
     */
    suspend fun getSkillsForSkillTree(
        skillTreeId: Int,
        language: String,
    ): List<Skill> {
        val skillsWithText = skillDao.getSkillListBySkillTreeId(skillTreeId, language)

        return skillsWithText.map { SkillMapper.toModel(it) }
    }

}
