package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.database.dao.ArmorSetDao
import com.gaugustini.mhfudatabase.data.mapper.ArmorMapper
import com.gaugustini.mhfudatabase.data.mapper.ArmorSetMapper
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.ArmorSet
import javax.inject.Inject
import javax.inject.Singleton

//TODO: Add skills and recipe for armors
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
        val armorWithText = armorDao.getArmor(armorId, language)

        return ArmorMapper.toModel(
            armor = armorWithText,
            skills = emptyList(),
            recipe = emptyList(),
        )
    }

    /**
     * Returns the list of all armors.
     */
    suspend fun getArmorList(
        language: String,
    ): List<Armor> {
        val armorsWithText = armorDao.getArmorList(language)

        return armorsWithText.map {
            ArmorMapper.toModel(
                armor = it,
                skills = emptyList(),
                recipe = emptyList(),
            )
        }
    }

    /**
     * Returns the list of armors for the given armor set ID.
     */
    suspend fun getArmorListForArmorSet(
        armorSetId: Int,
        language: String,
    ): List<Armor> {
        val armorsWithText = armorDao.getArmorListByArmorSetId(armorSetId, language)

        return armorsWithText.map {
            ArmorMapper.toModel(
                armor = it,
                skills = emptyList(),
                recipe = emptyList(),
            )
        }
    }

    /**
     * Returns the armor set with the given ID.
     */
    suspend fun getArmorSet(
        armorSetId: Int,
        language: String,
    ): ArmorSet {
        val armorSetWithText = armorSetDao.getArmorSet(armorSetId, language)
        val armors = getArmorListForArmorSet(armorSetId, language)

        return ArmorSetMapper.toModel(
            armorSet = armorSetWithText,
            armors = armors,
        )
    }

    /**
     * Returns the list of all armor sets.
     */
    suspend fun getArmorSetList(
        language: String,
    ): List<ArmorSet> {
        val armorSetsWithText = armorSetDao.getArmorSetList(language)
        val armorsGroupedByArmorSet = getArmorList(language).groupBy { it.armorSetId }

        return armorSetsWithText.map {
            ArmorSetMapper.toModel(
                armorSet = it,
                armors = armorsGroupedByArmorSet[it.armorSet.id] ?: emptyList(),
            )
        }
    }

}
