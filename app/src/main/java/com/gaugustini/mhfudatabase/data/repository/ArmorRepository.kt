package com.gaugustini.mhfudatabase.data.repository

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
        language: String = "en",
    ): List<Armor> {
        return armorDao.getArmorList(language)
    }

    suspend fun getArmorSetList(
        hunterType: HunterType,
        language: String = "en",
    ): List<ArmorSet> {
        return armorDao.getArmorSetsByHunterType(hunterType, language)
    }

    // Detail

    suspend fun getArmorDetails(
        armorId: Int,
        language: String = "en",
    ): ArmorDetails {
        return ArmorDetails(
            armor = armorDao.getArmor(armorId, language),
            skills = armorDao.getSkillsForArmor(armorId, language),
            recipe = armorDao.getItemsForArmor(armorId, language),
        )
    }

    suspend fun getArmorSetDetails(
        armorSetId: Int,
        language: String = "en",
    ): ArmorSetDetails {
        return ArmorSetDetails(
            armorSet = armorDao.getArmorSet(armorSetId, language),
            armors = armorDao.getArmorsForArmorSet(armorSetId, language),
            skills = armorDao.getSkillsForArmorSet(armorSetId, language),
            recipe = armorDao.getItemsForArmorSet(armorSetId, language),
        )
    }

}
