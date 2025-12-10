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
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.Skill
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints
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
            user_set.id = :id AND
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
            user_set_armor.user_set_id = :id AND
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
            user_set_decoration.user_set_id = :id AND
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

    // Skills Tree points in Set

    @Query(
        """
        SELECT
            armor_skill.skill_tree_id       AS id,
            skill_tree_text.name            AS name,
            SUM(armor_skill.point_value)    AS pointValue
        FROM user_set
        JOIN user_set_armor
            ON user_set.id = user_set_armor.user_set_id
        JOIN armor_skill
            ON user_set_armor.armor_id = armor_skill.armor_id
        JOIN skill_tree_text
            ON armor_skill.skill_tree_id = skill_tree_text.skill_tree_id
        WHERE
            user_set.id = :id AND
            skill_tree_text.language = :language
        GROUP BY skill_tree_text.skill_tree_id
        ORDER BY pointValue DESC;
        """
    )
    suspend fun getSkillTreePointsInArmors(id: Int, language: String): List<SkillTreePoints>

    @Query(
        """
        SELECT
            decoration_skill.skill_tree_id                                      AS id,
            skill_tree_text.name                                                AS name,
            SUM(decoration_skill.point_value * user_set_decoration.quantity)    AS pointValue
        FROM user_set
        JOIN user_set_decoration
            ON user_set.id = user_set_decoration.user_set_id
        JOIN decoration_skill
            ON user_set_decoration.decoration_id = decoration_skill.decoration_id
        JOIN skill_tree_text
            ON decoration_skill.skill_tree_id = skill_tree_text.skill_tree_id
        WHERE
            user_set.id = :id AND
            skill_tree_text.language = :language
        GROUP BY skill_tree_text.skill_tree_id
        ORDER BY pointValue DESC;
        """
    )
    suspend fun getSkillTreePointsInDecorations(id: Int, language: String): List<SkillTreePoints>

    // Active Skills in Set

    @Query(
        """
        SELECT
            skill.skill_tree_id     AS skillTreeId,
            skill_text.name         AS name,
            skill_text.description  AS description,
            skill.required_points   AS requiredPoints
        FROM skill
        JOIN skill_text
            ON skill.id = skill_text.skill_id
        WHERE
            skill.skill_tree_id IN (:ids) AND
            skill_text.language = :language
        ORDER BY requiredPoints DESC
        """
    )
    suspend fun getActiveSkillsForSet(ids: List<Int>, language: String): List<Skill>

    // Required Materials for set

    @Query(
        """
        SELECT
            weapon_recipe.item_id		AS id,
            item_text.name          	AS name,
            SUM(weapon_recipe.quantity)	AS quantity,
            item.icon_type          	AS iconType,
            item.icon_color         	AS iconColor
        FROM user_set
        JOIN weapon_recipe
            ON user_set.weapon_id = weapon_recipe.weapon_id
        JOIN item
            ON weapon_recipe.item_id = item.id
        JOIN item_text
            ON weapon_recipe.item_id = item_text.item_id
        WHERE
            user_set.id = :id AND
            item_text.language = :language
        GROUP BY item.id
        ORDER BY quantity DESC;
        """
    )
    suspend fun getItemsForWeapon(id: Int, language: String): List<ItemQuantity>

    @Query(
        """
        SELECT
            armor_recipe.item_id		AS id,
            item_text.name          	AS name,
            SUM(armor_recipe.quantity)	AS quantity,
            item.icon_type          	AS iconType,
            item.icon_color         	AS iconColor
        FROM user_set
        JOIN user_set_armor
            ON user_set.id = user_set_armor.user_set_id
        JOIN armor_recipe
            ON user_set_armor.armor_id = armor_recipe.armor_id
        JOIN item
            ON armor_recipe.item_id = item.id
        JOIN item_text
            ON armor_recipe.item_id = item_text.item_id
        WHERE
            user_set.id = :id AND
            item_text.language = :language
        GROUP BY item.id
        ORDER BY quantity DESC;
        """
    )
    suspend fun getItemsForArmors(id: Int, language: String): List<ItemQuantity>

    @Query(
        """
        SELECT
            decoration_recipe.item_id		AS id,
            item_text.name          	    AS name,
            SUM(decoration_recipe.quantity)	AS quantity,
            item.icon_type          	    AS iconType,
            item.icon_color         	    AS iconColor
        FROM user_set
        JOIN user_set_decoration
            ON user_set.id = user_set_decoration.user_set_id
        JOIN decoration_recipe
            ON user_set_decoration.decoration_id = decoration_recipe.decoration_id
        JOIN item
            ON decoration_recipe.item_id = item.id
        JOIN item_text
            ON decoration_recipe.item_id = item_text.item_id
        WHERE
            user_set.id = :id AND
            item_text.language = :language
        GROUP BY item.id
        ORDER BY quantity DESC;
        """
    )
    suspend fun getItemsForDecorations(id: Int, language: String): List<ItemQuantity>

}
