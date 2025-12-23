package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.DecorationDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Decoration.
 */
@Singleton
class DecorationRepository @Inject constructor(
    private val decorationDao: DecorationDao,
)
