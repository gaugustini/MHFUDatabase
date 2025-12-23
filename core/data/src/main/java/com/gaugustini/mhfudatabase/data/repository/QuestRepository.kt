package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.QuestDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Quest.
 */
@Singleton
class QuestRepository @Inject constructor(
    private val questDao: QuestDao,
)
