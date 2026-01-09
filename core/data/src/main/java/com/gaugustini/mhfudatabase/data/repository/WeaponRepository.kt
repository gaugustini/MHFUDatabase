package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.WeaponDao
import com.gaugustini.mhfudatabase.data.mapper.WeaponMapper
import com.gaugustini.mhfudatabase.domain.model.Weapon
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Weapon.
 */
@Singleton
class WeaponRepository @Inject constructor(
    private val weaponDao: WeaponDao,
) {

    /**
     * Returns the weapon with the given ID.
     */
    suspend fun getWeapon(
        weaponId: Int,
        language: String,
    ): Weapon {
        val weaponWithText = weaponDao.getWeapon(weaponId, language)

        return WeaponMapper.map(weaponWithText)
    }

    /**
     * Returns the list of all weapons.
     */
    suspend fun getWeaponList(
        language: String,
    ): List<Weapon> {
        val weaponsWithText = weaponDao.getWeaponList(language)

        return WeaponMapper.mapList(weaponsWithText)
    }

}
