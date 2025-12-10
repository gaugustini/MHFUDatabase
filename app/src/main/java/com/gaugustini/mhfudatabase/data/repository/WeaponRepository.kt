package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.database.dao.WeaponDao
import com.gaugustini.mhfudatabase.data.enums.WeaponType
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.data.model.WeaponDetails
import com.gaugustini.mhfudatabase.data.model.WeaponGraph
import com.gaugustini.mhfudatabase.data.model.WeaponNode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeaponRepository @Inject constructor(
    private val weaponDao: WeaponDao,
) {

    // Graph

    suspend fun getWeaponGraph(
        type: WeaponType,
        language: Language,
    ): List<WeaponNode> {
        val weaponList = weaponDao.getWeaponsByWeaponTypes(type.pairGroup, language.code)
        val weaponRelationList = weaponDao.getWeaponRelationsForWeapons(weaponList.map { it.id })

        return WeaponGraph(weaponList, weaponRelationList).buildGraphByType(type)
    }

    // Detail

    suspend fun getWeaponDetails(
        weaponId: Int,
        language: Language,
    ): WeaponDetails {
        val weapon = weaponDao.getWeapon(weaponId, language.code)
        val weaponList = weaponDao.getWeaponsByWeaponTypes(weapon.type.pairGroup, language.code)
        val weaponRelationList = weaponDao.getWeaponRelationsForWeapons(weaponList.map { it.id })
        val weaponGraph = WeaponGraph(weaponList, weaponRelationList)

        return WeaponDetails(
            weapon = weapon,
            ammoBow = weaponDao.getAmmoBowForWeapon(weaponId),
            ammoBowgun = weaponDao.getAmmoBowgunForWeapon(weaponId),
            recipeCreate = weaponDao.getItemsForWeapon(weaponId, "CREATE", language.code),
            recipeUpgrade = weaponDao.getItemsForWeapon(weaponId, "UPGRADE", language.code),
            paths = weaponGraph.findPathsToRoot(weaponId),
            finals = weaponGraph.findLeaves(weaponId),
        )
    }

    // User Equipment Set

    suspend fun getWeaponListForUserEquipmentSet(language: Language): List<Weapon> {
        return weaponDao.getWeaponListForUserEquipmentSet(language.code)
    }

}
