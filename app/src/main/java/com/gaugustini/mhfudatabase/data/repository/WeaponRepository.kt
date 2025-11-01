package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.database.dao.WeaponDao
import com.gaugustini.mhfudatabase.data.enums.WeaponType
import com.gaugustini.mhfudatabase.data.model.WeaponDetails
import com.gaugustini.mhfudatabase.data.model.WeaponGraph
import com.gaugustini.mhfudatabase.data.model.WeaponNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeaponRepository @Inject constructor(
    private val weaponDao: WeaponDao,
    userPreferences: UserPreferences,
) {
    private val currentLanguage: StateFlow<Language> = userPreferences.getLanguage()
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Eagerly,
            initialValue = Language.ENGLISH
        )

    // Graph

    suspend fun getWeaponGraph(
        type: WeaponType,
    ): List<WeaponNode> {
        val language = currentLanguage.value.code
        val weaponList = weaponDao.getWeaponsByWeaponTypes(type.pairGroup, language)
        val weaponRelationList = weaponDao.getWeaponRelationsForWeapons(weaponList.map { it.id })

        return WeaponGraph(weaponList, weaponRelationList).buildGraphByType(type)
    }

    // Detail

    suspend fun getWeaponDetails(
        weaponId: Int,
    ): WeaponDetails {
        val language = currentLanguage.value.code
        val weapon = weaponDao.getWeapon(weaponId, language)
        val weaponList = weaponDao.getWeaponsByWeaponTypes(weapon.type.pairGroup, language)
        val weaponRelationList = weaponDao.getWeaponRelationsForWeapons(weaponList.map { it.id })
        val weaponGraph = WeaponGraph(weaponList, weaponRelationList)

        return WeaponDetails(
            weapon = weapon,
            ammoBow = weaponDao.getAmmoBowForWeapon(weaponId),
            ammoBowgun = weaponDao.getAmmoBowgunForWeapon(weaponId),
            recipeCreate = weaponDao.getItemsForWeapon(weaponId, "CREATE", language),
            recipeUpgrade = weaponDao.getItemsForWeapon(weaponId, "UPGRADE", language),
            paths = weaponGraph.findPathsToRoot(weaponId),
            finals = weaponGraph.findLeaves(weaponId),
        )
    }

}
