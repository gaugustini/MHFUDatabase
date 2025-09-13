package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.enums.HunterType
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.ArmorDetails
import com.gaugustini.mhfudatabase.data.model.ArmorSetDetails
import com.gaugustini.mhfudatabase.data.model.ArmorSetSummary
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArmorRepository @Inject constructor(
    private val armorDao: ArmorDao,
) {

    // List

    suspend fun getArmorSetList(
        hunterType: HunterType,
        language: String = "en",
    ): List<ArmorSetSummary> {
        val armorSets = armorDao.getArmorSetsByHunterType(hunterType, language)
        val armors = armorDao.getArmorsByHunterType(hunterType, language)

        val armorsBySetId: Map<Int, List<Armor>> = armors.groupBy { it.armorSetId }

        return armorSets.map { set ->
            ArmorSetSummary(
                armorSet = set,
                armors = armorsBySetId[set.id] ?: emptyList()
            )
        }
    }

    // Detail

    suspend fun getArmorDetails(
        armorId: Int,
        language: String = "en",
    ): ArmorDetails {
        return ArmorDetails(
            armor = armorDao.getArmor(armorId, language),
            skills = armorDao.getSkillsForArmor(armorId, language),
            recipe = armorDao.getItemsForArmor(armorId, language)
        )
    }

    suspend fun getArmorSetDetails(
        armorSetId: Int,
        language: String = "en",
    ): ArmorSetDetails {
        val armors = armorDao.getArmorsForArmorSet(armorSetId, language)

        val totalDefense: Int = armors.sumOf { it.defense }
        val totalMaxDefense: Int = armors.sumOf { it.maxDefense }
        val totalFire: Int = armors.sumOf { it.fire }
        val totalWater: Int = armors.sumOf { it.water }
        val totalThunder: Int = armors.sumOf { it.thunder }
        val totalIce: Int = armors.sumOf { it.ice }
        val totalDragon: Int = armors.sumOf { it.dragon }

        return ArmorSetDetails(
            armorSet = armorDao.getArmorSet(armorSetId, language),
            defense = totalDefense,
            maxDefense = totalMaxDefense,
            fire = totalFire,
            water = totalWater,
            thunder = totalThunder,
            ice = totalIce,
            dragon = totalDragon,
            armors = armors,
            skills = emptyList(), // TODO: add skills
            recipe = emptyList(), // TODO: add recipe
        )
    }

}
