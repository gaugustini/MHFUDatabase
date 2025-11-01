package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import com.gaugustini.mhfudatabase.data.model.SkillTree
import com.gaugustini.mhfudatabase.data.model.SkillTreeDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SkillRepository @Inject constructor(
    private val skillDao: SkillDao,
    userPreferences: UserPreferences,
) {
    private val currentLanguage: StateFlow<Language> = userPreferences.getLanguage()
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Eagerly,
            initialValue = Language.ENGLISH
        )

    // List

    suspend fun getSkillTreeList(): List<SkillTree> {
        val language = currentLanguage.value.code
        return skillDao.getSkillTreeList(language)
    }

    // Detail

    suspend fun getSkillTreeDetails(
        skillTreeId: Int,
    ): SkillTreeDetails {
        val language = currentLanguage.value.code
        return SkillTreeDetails(
            skillTree = skillDao.getSkillTree(skillTreeId, language),
            skills = skillDao.getSkillsForSkillTree(skillTreeId, language),
            decorations = skillDao.getDecorationsWithSkillPoints(skillTreeId, language),
            armors = skillDao.getArmorsWithSkillPoints(skillTreeId, language),
        )
    }

}
