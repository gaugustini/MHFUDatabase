package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import com.gaugustini.mhfudatabase.data.database.dao.UserEquipmentSetDao
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentSkillTreePoint
import com.gaugustini.mhfudatabase.data.mapper.UserEquipmentSetMapper
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

/**
 * Data repository for User Equipment Set.
 */
@Singleton
class UserEquipmentSetRepository @Inject constructor(
    private val userEquipmentSetDao: UserEquipmentSetDao,
    private val skillDao: SkillDao,
) {

    /**
     * Returns the user equipment set with the given ID.
     */
    suspend fun getEquipmentSet(
        equipmentSetId: Int,
        language: String,
    ): UserEquipmentSet {
        val skillsPoint = getSkillTreePointsInSet(equipmentSetId, language)
        val activeSkills = skillsPoint.filter { abs(it.points) >= 10 }.mapNotNull {
            skillDao.getActiveSkill(it.skillTree.id, it.points, language)
        }

        return UserEquipmentSetMapper.toModel(
            equipmentSet = userEquipmentSetDao.getEquipmentSet(equipmentSetId),
            weapon = userEquipmentSetDao.getWeaponByUserSetId(equipmentSetId, language),
            armors = userEquipmentSetDao.getArmorsByUserSetId(equipmentSetId, language),
            decorations = userEquipmentSetDao.getDecorationsByUserSetId(equipmentSetId, language),
            activeSkills = activeSkills,
            skills = skillsPoint,
            recipe = getRequiredMaterialsForSet(equipmentSetId, language),
        )
    }

    /**
     * Returns the list of all user equipment sets.
     * Note: weapon, armors and decorations are not populated.
     */
    fun getEquipmentSets(): Flow<List<UserEquipmentSet>> {
        return userEquipmentSetDao.getEquipmentSets().map {
            it.map { entity -> UserEquipmentSetMapper.toModel(entity) }
        }
    }

    /**
     * Inserts a new user equipment set into the database.
     */
    suspend fun insertNewEquipmentSet(
        equipmentSet: UserEquipmentSet,
    ): Int {
        return userEquipmentSetDao.insertNewEquipmentSet(
            equipmentSet = UserEquipmentSetMapper.toEntity(equipmentSet),
            equipmentSetArmors = UserEquipmentSetMapper.toArmorEntities(equipmentSet),
            equipmentSetDecorations = UserEquipmentSetMapper.toDecorationEntities(equipmentSet)
        )
    }

    /**
     * Updates an existing user equipment set in the database.
     */
    suspend fun updateEquipmentSet(
        equipmentSet: UserEquipmentSet,
    ) {
        userEquipmentSetDao.updateEquipmentSet(
            equipmentSet = UserEquipmentSetMapper.toEntity(equipmentSet),
            equipmentSetArmors = UserEquipmentSetMapper.toArmorEntities(equipmentSet),
            equipmentSetDecorations = UserEquipmentSetMapper.toDecorationEntities(equipmentSet)
        )
    }

    /**
     * Deletes a user equipment set from the database.
     */
    suspend fun deleteEquipmentSet(
        equipmentSetId: Int,
    ) {
        userEquipmentSetDao.deleteEquipmentSet(equipmentSetId)
    }

    /**
     * Returns the skill tree points for the given equipment set id.
     * TODO: Add calculation for Torso Increased
     */
    private suspend fun getSkillTreePointsInSet(
        equipmentSetId: Int,
        language: String,
    ): List<EquipmentSkillTreePoint> {
        val armorsSkills = userEquipmentSetDao.getArmorSkillsByUserSetId(equipmentSetId, language)
        val decorationsSkills =
            userEquipmentSetDao.getDecorationSkillsByUserSetId(equipmentSetId, language)

        return (armorsSkills + decorationsSkills)
            .groupBy { it.skillTree.id }
            .map { (_, skills) ->
                skills.first().copy(points = skills.sumOf { it.points })
            }
    }

    /**
     * Returns the required materials for the given equipment set id.
     */
    private suspend fun getRequiredMaterialsForSet(
        equipmentSetId: Int,
        language: String,
    ): List<EquipmentItemQuantity> {
        var weaponMaterials =
            userEquipmentSetDao.getWeaponRecipeByUserSetId(equipmentSetId, "CREATE", language)
        if (weaponMaterials.isEmpty()) {
            weaponMaterials =
                userEquipmentSetDao.getWeaponRecipeByUserSetId(equipmentSetId, "UPGRADE", language)
        }
        val armorMaterials = userEquipmentSetDao.getArmorRecipesByUserSetId(equipmentSetId, language)
        val decorationMaterials =
            userEquipmentSetDao.getDecorationRecipesByUserSetId(equipmentSetId, language)

        return (weaponMaterials + armorMaterials + decorationMaterials)
            .groupBy { it.item.id }
            .map { (_, items) ->
                items.first().copy(quantity = items.sumOf { it.quantity })
            }
    }

}
