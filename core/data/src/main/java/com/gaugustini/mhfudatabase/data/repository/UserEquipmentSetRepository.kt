package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.UserEquipmentSetDao
import com.gaugustini.mhfudatabase.data.mapper.UserEquipmentSetMapper
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet
import javax.inject.Inject
import javax.inject.Singleton

//TODO: Add remain data (weapon, active skills, skills, recipe)
/**
 * Data repository for User Equipment Set.
 */
@Singleton
class UserEquipmentSetRepository @Inject constructor(
    private val userEquipmentSetDao: UserEquipmentSetDao,
) {

    /**
     * Returns the user equipment set with the given ID.
     */
    suspend fun getEquipmentSet(
        equipmentSetId: Int,
        language: String,
    ): UserEquipmentSet {
        return UserEquipmentSetMapper.toModel(
            equipmentSet = userEquipmentSetDao.getEquipmentSet(equipmentSetId),
            weapon = null,
            armors = emptyList(),
            decorations = emptyList(),
        )
    }

    /**
     * Returns the list of all user equipment sets.
     */
    suspend fun getEquipmentSets(): List<UserEquipmentSet> {
        return userEquipmentSetDao.getEquipmentSets().map { UserEquipmentSetMapper.toModel(it) }
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

}
