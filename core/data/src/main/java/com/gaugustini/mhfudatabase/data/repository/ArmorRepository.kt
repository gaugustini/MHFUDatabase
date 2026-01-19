package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.database.dao.ArmorSetDao
import com.gaugustini.mhfudatabase.data.mapper.ArmorMapper
import com.gaugustini.mhfudatabase.data.mapper.ArmorSetMapper
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.ArmorSet
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Armor and Armor Set.
 */
@Singleton
class ArmorRepository @Inject constructor(
    private val armorDao: ArmorDao,
    private val armorSetDao: ArmorSetDao,
) {

    /**
     * Returns the armor with the given ID.
     */
    suspend fun getArmor(
        armorId: Int,
        language: String,
    ): Armor {
        return ArmorMapper.toModel(
            armor = armorDao.getArmor(armorId, language),
            skills = armorDao.getArmorSkillsByArmorId(armorId, language),
            recipe = armorDao.getArmorRecipeByArmorId(armorId, language),
        )
    }

    /**
     * Returns the armor set with the given ID.
     */
    suspend fun getArmorSet(
        armorSetId: Int,
        language: String,
    ): ArmorSet {
        return ArmorSetMapper.toModel(
            armorSet = armorSetDao.getArmorSet(armorSetId, language),
            armors = armorDao.getArmorListByArmorSetId(armorSetId, language),
            skills = armorSetDao.getArmorSetSkillsByArmorSetId(armorSetId, language),
            recipe = armorSetDao.getArmorSetRecipeByArmorSetId(armorSetId, language),
        )
    }

    /**
     * Returns the list of all armor sets.
     * Note: skills and recipe are not populated.
     */
    suspend fun getArmorSetList(
        language: String,
    ): List<ArmorSet> {
        val armorSetsWithText = armorSetDao.getArmorSetList(language)
        val armorsGroupedByArmorSet = armorDao.getArmorList(language).groupBy { it.armor.armorSetId }

        return armorSetsWithText.map {
            ArmorSetMapper.toModel(
                armorSet = it,
                armors = armorsGroupedByArmorSet[it.armorSet.id] ?: emptyList(),
                skills = emptyList(),
                recipe = emptyList(),
            )
        }
    }

}
