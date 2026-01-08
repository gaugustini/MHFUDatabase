package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetDecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetEntity

/**
 * [Dao] for User Equipment Set related database operations.
 */
@Dao
interface UserEquipmentSetDao {

    @Query("SELECT * FROM user_set WHERE id = :equipmentSetId")
    suspend fun getEquipmentSet(equipmentSetId: Int): UserEquipmentSetEntity

    @Query("SELECT * FROM user_set")
    suspend fun getEquipmentSets(): List<UserEquipmentSetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipmentSet(equipmentSet: UserEquipmentSetEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipmentSetArmors(equipmentSetArmors: List<UserEquipmentSetArmorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipmentSetDecorations(equipmentSetDecorations: List<UserEquipmentSetDecorationEntity>)

    @Transaction
    suspend fun insertNewEquipmentSet(
        equipmentSet: UserEquipmentSetEntity,
        equipmentSetArmors: List<UserEquipmentSetArmorEntity>,
        equipmentSetDecorations: List<UserEquipmentSetDecorationEntity>,
    ): Int {
        val newEquipmentSetId = insertEquipmentSet(equipmentSet).toInt()
        val armors = equipmentSetArmors.map { armor ->
            armor.copy(userSetId = newEquipmentSetId)
        }
        val decorations = equipmentSetDecorations.map { decoration ->
            decoration.copy(userSetId = newEquipmentSetId)
        }
        insertEquipmentSetArmors(armors)
        insertEquipmentSetDecorations(decorations)
        return newEquipmentSetId
    }

    @Update
    suspend fun updateEquipmentSet(equipmentSet: UserEquipmentSetEntity)

    @Transaction
    suspend fun updateEquipmentSet(
        equipmentSet: UserEquipmentSetEntity,
        equipmentSetArmors: List<UserEquipmentSetArmorEntity>,
        equipmentSetDecorations: List<UserEquipmentSetDecorationEntity>,
    ) {
        updateEquipmentSet(equipmentSet)
        deleteEquipmentSetArmors(equipmentSet.id)
        deleteEquipmentSetDecorations(equipmentSet.id)
        insertEquipmentSetArmors(equipmentSetArmors)
        insertEquipmentSetDecorations(equipmentSetDecorations)
    }

    @Query("DELETE FROM user_set WHERE id = :equipmentSetId")
    suspend fun deleteEquipmentSet(equipmentSetId: Int)

    @Query("DELETE FROM user_set_armor WHERE user_set_id = :equipmentSetId")
    suspend fun deleteEquipmentSetArmors(equipmentSetId: Int)

    @Query("DELETE FROM user_set_decoration WHERE user_set_id = :equipmentSetId")
    suspend fun deleteEquipmentSetDecorations(equipmentSetId: Int)

}
