package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.WeaponDao
import com.gaugustini.mhfudatabase.data.mapper.WeaponMapper
import com.gaugustini.mhfudatabase.domain.model.Weapon
import javax.inject.Inject
import javax.inject.Singleton

//TODO: Add weapon remain data (ammo, charges, rapid fire, ...); Create weapon tree
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
        return WeaponMapper.toModel(
            weapon = weaponDao.getWeapon(weaponId, language),
            ammoBow = null,
            ammoBowgun = null,
            charges = null,
            rapidFire = null,
            recipeCreate = emptyList(),
            recipeUpgrade = emptyList(),
        )
    }

    /**
     * Returns the list of all weapons.
     */
    suspend fun getWeaponList(
        language: String,
    ): List<Weapon> {
        return weaponDao.getWeaponList(language).map { WeaponMapper.toModel(it) }
    }

}
