package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.MonsterDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Monster.
 */
@Singleton
class MonsterRepository @Inject constructor(
    private val monsterDao: MonsterDao,
)
