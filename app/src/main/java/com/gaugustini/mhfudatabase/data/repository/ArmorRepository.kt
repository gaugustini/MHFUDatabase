package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.enums.HunterType
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.ArmorDetails
import com.gaugustini.mhfudatabase.data.model.ArmorSet
import com.gaugustini.mhfudatabase.data.model.ArmorSetDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArmorRepository @Inject constructor(
    private val armorDao: ArmorDao,
) {

    // List

    suspend fun getArmorList(
        language: Language,
    ): List<Armor> {
        return armorDao.getArmorList(language.code)
    }

    suspend fun getArmorSetList(
        hunterType: HunterType,
        language: Language,
    ): List<ArmorSet> {
        return armorDao.getArmorSetsByHunterType(hunterType, language.code)
    }

    // Detail

    suspend fun getArmorDetails(
        armorId: Int,
        language: Language,
    ): ArmorDetails {
        return ArmorDetails(
            armor = armorDao.getArmor(armorId, language.code),
            skills = armorDao.getSkillsForArmor(armorId, language.code),
            recipe = armorDao.getItemsForArmor(armorId, language.code),
        )
    }

    suspend fun getArmorSetDetails(
        armorSetId: Int,
        language: Language,
    ): ArmorSetDetails {
        return ArmorSetDetails(
            armorSet = armorDao.getArmorSet(armorSetId, language.code),
            armors = armorDao.getArmorsForArmorSet(armorSetId, language.code),
            skills = armorDao.getSkillsForArmorSet(armorSetId, language.code),
            recipe = armorDao.getItemsForArmorSet(armorSetId, language.code),
        )
    }

}
