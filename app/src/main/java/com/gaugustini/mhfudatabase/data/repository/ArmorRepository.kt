package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.enums.HunterType
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.ArmorDetails
import com.gaugustini.mhfudatabase.data.model.ArmorSet
import com.gaugustini.mhfudatabase.data.model.ArmorSetDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArmorRepository @Inject constructor(
    private val armorDao: ArmorDao,
    userPreferences: UserPreferences,
) {
    private val currentLanguage: StateFlow<Language> = userPreferences.getLanguage()
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Eagerly,
            initialValue = Language.ENGLISH
        )

    // List

    suspend fun getArmorList(): List<Armor> {
        val language = currentLanguage.value.code
        return armorDao.getArmorList(language).sortedBy { it.type }
    }

    suspend fun getArmorSetList(
        hunterType: HunterType,
    ): List<ArmorSet> {
        val language = currentLanguage.value.code
        return armorDao.getArmorSetsByHunterType(hunterType, language)
    }

    // Detail

    suspend fun getArmorDetails(
        armorId: Int,
    ): ArmorDetails {
        val language = currentLanguage.value.code
        return ArmorDetails(
            armor = armorDao.getArmor(armorId, language),
            skills = armorDao.getSkillsForArmor(armorId, language),
            recipe = armorDao.getItemsForArmor(armorId, language),
        )
    }

    suspend fun getArmorSetDetails(
        armorSetId: Int,
    ): ArmorSetDetails {
        val language = currentLanguage.value.code
        return ArmorSetDetails(
            armorSet = armorDao.getArmorSet(armorSetId, language),
            armors = armorDao.getArmorsForArmorSet(armorSetId, language).sortedBy { it.type },
            skills = armorDao.getSkillsForArmorSet(armorSetId, language),
            recipe = armorDao.getItemsForArmorSet(armorSetId, language),
        )
    }

}
