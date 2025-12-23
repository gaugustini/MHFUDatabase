package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.database.dao.ArmorSetDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Armor and Armor Set.
 */
@Singleton
class ArmorRepository @Inject constructor(
    private val armorDao: ArmorDao,
    private val armorSetDao: ArmorSetDao,
)
