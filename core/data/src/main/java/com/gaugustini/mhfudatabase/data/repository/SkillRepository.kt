package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithText
import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithText
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentSkillTreePoint
import com.gaugustini.mhfudatabase.data.mapper.ArmorMapper
import com.gaugustini.mhfudatabase.data.mapper.DecorationMapper
import com.gaugustini.mhfudatabase.data.mapper.SkillTreeMapper
import com.gaugustini.mhfudatabase.domain.filter.SkillTreeFilter
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.domain.model.SkillTree
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Skill.
 */
@Singleton
class SkillRepository @Inject constructor(
    private val skillDao: SkillDao,
) {

    /**
     * Returns the skill tree with the given ID.
     */
    suspend fun getSkillTree(
        skillTreeId: Int,
        language: String,
    ): SkillTree {
        return SkillTreeMapper.toModel(
            skillTree = skillDao.getSkillTree(skillTreeId, language),
            skills = skillDao.getSkillListBySkillTreeId(skillTreeId, language),
        )
    }

    /**
     * Returns the list of all skill trees or filtering by [SkillTreeFilter].
     * Note: skills are not populated.
     */
    suspend fun getSkillTreeList(
        language: String,
        filter: SkillTreeFilter = SkillTreeFilter(),
    ): List<SkillTree> {
        return skillDao.getSkillTreeList(language).map { SkillTreeMapper.toModel(it) }
    }

    /**
     * Returns a list of armors with the given skill tree ID.
     */
    suspend fun getArmorListWithSkill(
        skillTreeId: Int,
        language: String,
    ): List<Armor> {
        return skillDao.getArmorListWithSkill(skillTreeId, language).map {
            ArmorMapper.toModel(
                armor = ArmorWithText(it.armor, it.armorText),
                skills = listOf(
                    EquipmentSkillTreePoint(
                        equipmentId = it.armor.id,
                        skillTree = it.skillTree,
                        skillTreeText = it.skillTreeText,
                        points = it.points,
                    ),
                ),
            )
        }
    }

    /**
     * Returns a list of decorations with the given skill tree ID.
     */
    suspend fun getDecorationListWithSkill(
        skillTreeId: Int,
        language: String,
    ): List<Decoration> {
        return skillDao.getDecorationListWithSkill(skillTreeId, language).map {
            DecorationMapper.toModel(
                decoration = DecorationWithText(it.decoration, it.item, it.itemText),
                skills = listOf(
                    EquipmentSkillTreePoint(
                        equipmentId = it.decoration.id,
                        skillTree = it.skillTree,
                        skillTreeText = it.skillTreeText,
                        points = it.points,
                    ),
                ),
            )
        }
    }

}
