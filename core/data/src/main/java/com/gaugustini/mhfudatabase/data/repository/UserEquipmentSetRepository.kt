package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.UserEquipmentSetDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for User Equipment Set.
 */
@Singleton
class UserEquipmentSetRepository @Inject constructor(
    private val userEquipmentSetDao: UserEquipmentSetDao,
)
