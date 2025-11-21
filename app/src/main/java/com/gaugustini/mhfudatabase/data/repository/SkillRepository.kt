package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
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
        language: Language,
    ): List<SkillTree> {
        return skillDao.getSkillTreeList(language.code)
    }

    // Detail

    suspend fun getSkillTreeDetails(
        skillTreeId: Int,
        language: Language,
    ): SkillTreeDetails {
        return SkillTreeDetails(
            skillTree = skillDao.getSkillTree(skillTreeId, language.code),
            skills = skillDao.getSkillsForSkillTree(skillTreeId, language.code),
            decorations = skillDao.getDecorationsWithSkillPoints(skillTreeId, language.code),
            armors = skillDao.getArmorsWithSkillPoints(skillTreeId, language.code),
        )
    }

}
