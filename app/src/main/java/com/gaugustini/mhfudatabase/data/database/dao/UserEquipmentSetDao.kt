package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.gaugustini.mhfudatabase.data.database.entity.UserEquipmentSetArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.UserEquipmentSetDecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.UserEquipmentSetEntity
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.EquipmentSetDecoration
import com.gaugustini.mhfudatabase.data.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.data.model.Weapon
import kotlinx.coroutines.flow.Flow

@Dao
interface UserEquipmentSetDao {

    // List

    @Query(
        """
        SELECT
            user_set.id     AS id,
            user_set.name   AS name
        FROM user_set
        """
    )
    fun getEquipmentSets(): Flow<List<UserEquipmentSet>>

    // Detail

    @Query(
        """
        SELECT
            user_set.id     AS id,
            user_set.name   AS name
        FROM user_set
        WHERE
            user_set.id = :id
        """
    )
    suspend fun getEquipmentSet(id: Int): UserEquipmentSet

    @Query(
        """
        SELECT
            weapon.id                AS id,
            weapon_text.name         AS name,
            weapon_text.description  AS description,
            weapon.weapon_type       AS type,
            weapon.rarity            AS rarity,
            weapon.affinity          AS affinity,
            weapon.defense           AS defense,
            weapon.num_slots         AS numSlots,
            weapon.attack            AS attack,
            weapon.max_attack        AS maxAttack,
            weapon.price_create      AS priceCreate,
            weapon.price_upgrade     AS priceUpgrade,
            weapon.element_1         AS element1,
            weapon.element_1_value   AS element1Value,
            weapon.element_2         AS element2,
            weapon.element_2_value   AS element2Value,
            weapon.sharpness         AS sharpness,
            weapon.sharpness_plus    AS sharpnessPlus,
            weapon.shelling_type     AS shellingType,
            weapon.shelling_level    AS shellingLevel,
            weapon.song_notes        AS songNotes,
            weapon.reload_speed      AS reloadSpeed,
            weapon.recoil            AS recoil
        FROM user_set
        JOIN weapon
            ON user_set.weapon_id = weapon.id
        JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
        WHERE
            user_set.weapon_id = :id AND
            weapon_text.language = :language
        """
    )
    suspend fun getWeaponForSet(id: Int, language: String): Weapon?

    @Query(
        """
        SELECT
            armor.id                AS id,
            armor.armor_set_id      AS armorSetId,
            armor_text.name         AS name,
            armor_text.description  AS description,
            armor.armor_type        AS type,
            armor.hunter_type       AS hunterType,
            armor.gender            AS gender,
            armor.rarity            AS rarity,
            armor.price             AS price,
            armor.num_slots         AS numberOfSlots,
            armor.defense           AS defense,
            armor.max_defense       AS maxDefense,
            armor.fire_res          AS fire,
            armor.water_res         AS water,
            armor.thunder_res       AS thunder,
            armor.ice_res           AS ice,
            armor.dragon_res        AS dragon
        FROM user_set_armor
        JOIN armor
            ON user_set_armor.armor_id = armor.id
        JOIN armor_text
            ON armor.id = armor_text.armor_id
        WHERE
            user_set_armor.armor_id = :id AND
            armor_text.language = :language
        """
    )
    suspend fun getArmorsForSet(id: Int, language: String): List<Armor>

    @Query(
        """
        SELECT
            user_set_decoration.user_set_id     AS setId,
            user_set_decoration.decoration_id   AS decorationId,
            item_text.name                      AS name,
            decoration.required_slots           AS requiredSlots,
            item.icon_color                     AS decorationColor,
            user_set_decoration.equipment_type  AS equipmentType,
            user_set_decoration.quantity        AS quantity
        FROM user_set_decoration
        JOIN decoration
            ON user_set_decoration.decoration_id = decoration.id
        JOIN item
            ON user_set_decoration.decoration_id = item.id
        JOIN item_text
            ON user_set_decoration.decoration_id = item_text.item_id
        WHERE
            user_set_decoration.decoration_id = :id AND
            item_text.language = :language
        """
    )
    suspend fun getDecorationsForSet(id: Int, language: String): List<EquipmentSetDecoration>

    // Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: UserEquipmentSetEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetArmors(armors: List<UserEquipmentSetArmorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetDecorations(decorations: List<UserEquipmentSetDecorationEntity>)

    @Transaction
    suspend fun insertNewSet(
        set: UserEquipmentSetEntity,
        setArmors: List<UserEquipmentSetArmorEntity>,
        setDecorations: List<UserEquipmentSetDecorationEntity>,
    ) {
        val newSetId = insertSet(set).toInt()
        val armors = setArmors.map { armor ->
            armor.copy(userSetId = newSetId)
        }
        val decorations = setDecorations.map { decoration ->
            decoration.copy(userSetId = newSetId)
        }
        insertSetArmors(armors)
        insertSetDecorations(decorations)
    }

    // Update

    @Update
    suspend fun updateSet(set: UserEquipmentSetEntity)

    @Transaction
    suspend fun updateSet(
        set: UserEquipmentSetEntity,
        setArmors: List<UserEquipmentSetArmorEntity>,
        setDecorations: List<UserEquipmentSetDecorationEntity>,
    ) {
        updateSet(set)
        deleteSetArmors(set.id)
        deleteSetDecorations(set.id)
        insertSetArmors(setArmors)
        insertSetDecorations(setDecorations)
    }

    // Delete

    @Query("DELETE FROM user_set WHERE id = :id")
    suspend fun deleteSet(id: Int)

    @Query("DELETE FROM user_set_armor WHERE user_set_id = :id")
    suspend fun deleteSetArmors(id: Int)

    @Query("DELETE FROM user_set_decoration WHERE user_set_id = :id")
    suspend fun deleteSetDecorations(id: Int)

}
