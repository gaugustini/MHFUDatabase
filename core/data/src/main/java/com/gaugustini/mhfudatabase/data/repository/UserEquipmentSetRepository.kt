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
        val equipmentSet = userEquipmentSetDao.getEquipmentSet(equipmentSetId)

        return UserEquipmentSetMapper.map(equipmentSet, null, emptyList(), emptyList())
    }

    /**
     * Returns the list of all user equipment sets.
     */
    suspend fun getEquipmentSets(): List<UserEquipmentSet> {
        val equipmentSets = userEquipmentSetDao.getEquipmentSets()

        return UserEquipmentSetMapper.mapList(equipmentSets)
    }

    /**
     * Inserts a new user equipment set into the database.
     */
    suspend fun insertNewEquipmentSet(
        equipmentSet: UserEquipmentSet,
    ): Int {
        val equipmentSetEntity = UserEquipmentSetMapper.mapToEntity(equipmentSet)
        val equipmentSetArmorsEntity = UserEquipmentSetMapper.mapToArmorEntities(equipmentSet)
        val equipmentSetDecorationsEntity = UserEquipmentSetMapper.mapToDecorationEntities(equipmentSet)

        return userEquipmentSetDao.insertNewEquipmentSet(
            equipmentSetEntity,
            equipmentSetArmorsEntity,
            equipmentSetDecorationsEntity
        )
    }

    /**
     * Updates an existing user equipment set in the database.
     */
    suspend fun updateEquipmentSet(
        equipmentSet: UserEquipmentSet,
    ) {
        val equipmentSetEntity = UserEquipmentSetMapper.mapToEntity(equipmentSet)
        val equipmentSetArmorsEntity = UserEquipmentSetMapper.mapToArmorEntities(equipmentSet)
        val equipmentSetDecorationsEntity = UserEquipmentSetMapper.mapToDecorationEntities(equipmentSet)

        userEquipmentSetDao.updateEquipmentSet(
            equipmentSetEntity,
            equipmentSetArmorsEntity,
            equipmentSetDecorationsEntity
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
