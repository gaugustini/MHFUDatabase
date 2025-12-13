package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.database.dao.UserEquipmentSetDao
import com.gaugustini.mhfudatabase.data.database.entity.UserEquipmentSetArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.UserEquipmentSetDecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.UserEquipmentSetEntity
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.Skill
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints
import com.gaugustini.mhfudatabase.data.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.data.model.UserEquipmentSetDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEquipmentSetRepository @Inject constructor(
    private val userEquipmentSetDao: UserEquipmentSetDao,
) {

    // List

    fun getEquipmentSets(): Flow<List<UserEquipmentSet>> {
        return userEquipmentSetDao.getEquipmentSets()
    }

    // Detail

    suspend fun getEquipmentSetDetails(
        id: Int,
        language: Language,
    ): UserEquipmentSetDetails {
        return UserEquipmentSetDetails(
            set = userEquipmentSetDao.getEquipmentSet(id),
            weapon = userEquipmentSetDao.getWeaponForSet(id, language.code),
            armors = userEquipmentSetDao.getArmorsForSet(id, language.code),
            decorations = userEquipmentSetDao.getDecorationsForSet(id, language.code),
        )
    }

    // Insert

    suspend fun insertNewSet(
        newSet: UserEquipmentSetDetails,
    ): Int {
        val setEntity = UserEquipmentSetEntity(
            id = 0,
            name = newSet.set.name,
            weaponId = newSet.weapon?.id,
        )
        val setArmorsEntity = newSet.armors.map { armor ->
            UserEquipmentSetArmorEntity(
                userSetId = 0,
                armorId = armor.id,
            )
        }
        val setDecorationsEntity = newSet.decorations.map { decoration ->
            UserEquipmentSetDecorationEntity(
                userSetId = 0,
                decorationId = decoration.decorationId,
                equipmentType = decoration.equipmentType,
                quantity = decoration.quantity,
            )
        }

        return userEquipmentSetDao.insertNewSet(setEntity, setArmorsEntity, setDecorationsEntity)
    }

    // Update

    suspend fun updateSet(
        set: UserEquipmentSetDetails,
    ) {
        val setEntity = UserEquipmentSetEntity(
            id = set.set.id,
            name = set.set.name,
            weaponId = set.weapon?.id,
        )
        val setArmorsEntity = set.armors.map { armor ->
            UserEquipmentSetArmorEntity(
                userSetId = set.set.id,
                armorId = armor.id,
            )
        }
        val setDecorationsEntity = set.decorations.map { decoration ->
            UserEquipmentSetDecorationEntity(
                userSetId = set.set.id,
                decorationId = decoration.decorationId,
                equipmentType = decoration.equipmentType,
                quantity = decoration.quantity,
            )
        }

        userEquipmentSetDao.updateSet(setEntity, setArmorsEntity, setDecorationsEntity)
    }

    // Delete

    suspend fun deleteSet(id: Int) {
        userEquipmentSetDao.deleteSet(id)
    }

    // Skills Tree points in Set

    suspend fun getSkillTreePointsInSet(
        setId: Int,
        language: Language,
    ): List<SkillTreePoints> {
        // TODO: Add calculation for Torso Increased

        val armorsSkills = userEquipmentSetDao.getSkillTreePointsInArmors(setId, language.code)
        val decorationsSkills = userEquipmentSetDao.getSkillTreePointsInDecorations(setId, language.code)
        val allSkills = armorsSkills + decorationsSkills

        return allSkills.groupBy { it.id }
            .map { (_, skills) ->
                skills.first().copy(
                    pointValue = skills.sumOf { it.pointValue }
                )
            }
    }

    // Active Skills in Set

    suspend fun getActiveSkillsForSet(
        setId: Int,
        language: Language,
    ): List<Skill> {
        val skillPointsInSet = getSkillTreePointsInSet(setId, language)

        val potentialActiveSkillPoints = skillPointsInSet.filter { skill ->
            skill.pointValue >= 10 || skill.pointValue <= -10
        }
        if (potentialActiveSkillPoints.isEmpty()) {
            return emptyList()
        }

        val attainableSkills = userEquipmentSetDao.getActiveSkillsForSet(
            potentialActiveSkillPoints.map { it.id }, language.code
        )

        val skillsByTreeId = attainableSkills.groupBy { it.skillTreeId }

        return potentialActiveSkillPoints.mapNotNull { skillPoint ->
            val skillsForThisTree = skillsByTreeId[skillPoint.id] ?: return@mapNotNull null

            findBestMatchingSkill(skillPoint.pointValue, skillsForThisTree)
        }
    }

    private fun findBestMatchingSkill(
        pointValue: Int,
        skillsForTree: List<Skill>
    ): Skill? {
        return if (pointValue >= 10) {
            skillsForTree
                .filter { it.requiredPoints in 10..pointValue }
                .maxByOrNull { it.requiredPoints }
        } else if (pointValue <= -10) {
            skillsForTree
                .filter { it.requiredPoints in pointValue..-10 }
                .minByOrNull { it.requiredPoints }
        } else {
            null
        }
    }

    // Required Materials for set

    suspend fun getRequiredMaterialsForSet(
        setId: Int,
        language: Language,
    ): List<ItemQuantity> {
        val weaponMaterials = userEquipmentSetDao.getItemsForWeapon(setId, language.code)
        val armorMaterials = userEquipmentSetDao.getItemsForArmors(setId, language.code)
        val decorationMaterials = userEquipmentSetDao.getItemsForDecorations(setId, language.code)

        val allMaterials = weaponMaterials + armorMaterials + decorationMaterials

        return allMaterials
            .groupBy { it.id }
            .map { (_, items) ->
                items.first().copy(
                    quantity = items.sumOf { it.quantity }
                )
            }
    }

}
