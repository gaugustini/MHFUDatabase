package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.database.dao.UserEquipmentSetDao
import com.gaugustini.mhfudatabase.data.database.entity.UserEquipmentSetArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.UserEquipmentSetDecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.UserEquipmentSetEntity
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
    ) {
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

        userEquipmentSetDao.insertNewSet(setEntity, setArmorsEntity, setDecorationsEntity)
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

}
