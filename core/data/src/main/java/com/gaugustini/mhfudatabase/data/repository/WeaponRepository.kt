package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.WeaponDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Weapon.
 */
@Singleton
class WeaponRepository @Inject constructor(
    private val weaponDao: WeaponDao,
)
