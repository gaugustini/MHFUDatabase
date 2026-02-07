package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.database.dao.ArmorSetDao
import com.gaugustini.mhfudatabase.data.mapper.ArmorMapper
import com.gaugustini.mhfudatabase.data.mapper.ArmorSetMapper
import com.gaugustini.mhfudatabase.domain.filter.ArmorFilter
import com.gaugustini.mhfudatabase.domain.filter.ArmorSetFilter
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
     * Returns the list of all armor or filtering by [ArmorFilter].
     * Note: skills and recipe are not populated.
     */
    suspend fun getArmorList(
        language: String,
        filter: ArmorFilter = ArmorFilter(),
    ): List<Armor> {
        return armorDao.getArmorList(
            language = language,
            name = filter.name,
            equipmentType = filter.type?.toString(),
            numberOfSlots = filter.numberOfSlots,
            hasSlotFilter = !filter.numberOfSlots.isNullOrEmpty(),
            rarity = filter.rarity,
            hasRarityFilter = !filter.rarity.isNullOrEmpty(),
            gender = filter.gender?.name,
            hunterType = filter.hunterType?.name,
            skills = filter.skills,
            hasSkillFilter = !filter.skills.isNullOrEmpty(),
        ).map { ArmorMapper.toModel(it) }
    }

    /**
     * Returns the list of all armor sets or filtering by [ArmorSetFilter].
     * Note: skills and recipe are not populated.
     */
    suspend fun getArmorSetList(
        language: String,
        filter: ArmorSetFilter = ArmorSetFilter(),
    ): List<ArmorSet> {
        val armorSetsWithText = armorSetDao.getArmorSetList(
            language = language,
            name = filter.name,
            rarity = filter.rarity,
            hasRarityFilter = !filter.rarity.isNullOrEmpty(),
            rank = filter.rank?.name,
            hunterType = filter.hunterType?.name,
            skills = filter.skills,
            hasSkillFilter = !filter.skills.isNullOrEmpty(),
        )
        val armorsGroupedByArmorSet = armorDao.getArmorList(
            language = language,
            name = null,
            equipmentType = null,
            numberOfSlots = null,
            hasSlotFilter = false,
            rarity = null,
            hasRarityFilter = false,
            gender = null,
            hunterType = null,
            skills = null,
            hasSkillFilter = false,
        ).groupBy { it.armor.armorSetId }

        return armorSetsWithText.map {
            ArmorSetMapper.toModel(
                armorSet = it,
                armors = armorsGroupedByArmorSet[it.armorSet.id],
            )
        }
    }

}
