package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import com.gaugustini.mhfudatabase.data.model.SkillTree
import com.gaugustini.mhfudatabase.data.model.SkillTreeDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkillRepository @Inject constructor(
    private val skillDao: SkillDao,
) {

    // List

    suspend fun getSkillTreeList(
        language: String = "en",
    ): List<SkillTree> {
        return skillDao.getSkillTreeList(language)
    }

    // Detail

    suspend fun getSkillTreeDetails(
        skillTreeId: Int,
        language: String = "en",
    ): SkillTreeDetails {
        return SkillTreeDetails(
            skillTree = skillDao.getSkillTree(skillTreeId, language),
            skills = skillDao.getSkillsForSkillTree(skillTreeId, language),
            decorations = skillDao.getDecorationsWithSkillPoints(skillTreeId, language),
            armors = skillDao.getArmorsWithSkillPoints(skillTreeId, language),
        )
    }

}
