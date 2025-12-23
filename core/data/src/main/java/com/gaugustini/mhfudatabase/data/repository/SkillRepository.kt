package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Skill.
 */
@Singleton
class SkillRepository @Inject constructor(
    private val skillDao: SkillDao,
)
