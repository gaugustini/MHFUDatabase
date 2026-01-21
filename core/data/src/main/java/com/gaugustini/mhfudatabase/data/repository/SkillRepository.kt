package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import com.gaugustini.mhfudatabase.data.mapper.SkillTreeMapper
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
        return SkillTreeMapper.toModel(
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
        return skillDao.getSkillTreeList(language).map { SkillTreeMapper.toModel(it) }
    }

}
