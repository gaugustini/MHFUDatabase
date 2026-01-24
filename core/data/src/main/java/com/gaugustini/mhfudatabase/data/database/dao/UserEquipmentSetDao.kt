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
import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithText
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentSkillTreePoint
import com.gaugustini.mhfudatabase.data.database.relation.UserSetDecorationWithDecoration
import com.gaugustini.mhfudatabase.data.database.relation.WeaponWithText

/**
 * [Dao] for User Equipment Set related database operations.
 */
@Dao
interface UserEquipmentSetDao {

    @Query(
        """
        SELECT
            user_set.*
        FROM user_set
        WHERE id = :equipmentSetId
        """
    )
    suspend fun getEquipmentSet(equipmentSetId: Int): UserEquipmentSetEntity

    @Query(
        """
        SELECT
            user_set.*
        FROM user_set
        """
    )
    suspend fun getEquipmentSets(): List<UserEquipmentSetEntity>

    @Query(
        """
        SELECT
            weapon.*,
            weapon_text.*
        FROM user_set
        JOIN weapon
            ON user_set.weapon_id = weapon.id
        JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = :language
        WHERE user_set.id = :equipmentSetId
        """
    )
    suspend fun getWeaponByUserSetId(equipmentSetId: Int, language: String): WeaponWithText?

    @Query(
        """
        SELECT
            armor.*,
            armor_text.*
        FROM user_set_armor
        JOIN armor
            ON user_set_armor.armor_id = armor.id
        JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = :language
        WHERE user_set_armor.user_set_id = :equipmentSetId
        """
    )
    suspend fun getArmorsByUserSetId(equipmentSetId: Int, language: String): List<ArmorWithText>

    @Query(
        """
        SELECT
            usd.user_set_id AS usd_user_set_id, usd.decoration_id AS usd_decoration_id, usd.equipment_type AS usd_equipment_type, usd.quantity AS usd_quantity,
            decoration.id AS dec_id, decoration.required_slots AS dec_required_slots,
            item.*,
            item_text.*
        FROM user_set_decoration usd
        JOIN decoration
            ON usd.decoration_id = decoration.id
        JOIN item
            ON decoration.id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE usd.user_set_id = :equipmentSetId
        """
    )
    suspend fun getDecorationsByUserSetId(
        equipmentSetId: Int,
        language: String
    ): List<UserSetDecorationWithDecoration>

    @Query(
        """
        SELECT
            user_set_armor.user_set_id AS equipmentId,
            skill_tree.*,
            skill_tree_text.*,
            SUM(armor_skill.point_value) AS points
        FROM user_set_armor
        JOIN armor_skill
            ON user_set_armor.armor_id = armor_skill.armor_id
        JOIN skill_tree
            ON armor_skill.skill_tree_id = skill_tree.id
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = :language
        WHERE user_set_armor.user_set_id = :equipmentSetId
        GROUP BY skill_tree.id
        """
    )
    suspend fun getArmorSkillsByUserSetId(
        equipmentSetId: Int,
        language: String
    ): List<EquipmentSkillTreePoint>

    @Query(
        """
        SELECT
            user_set_decoration.user_set_id AS equipmentId,
            skill_tree.*,
            skill_tree_text.*,
            SUM(decoration_skill.point_value * user_set_decoration.quantity) AS points
        FROM user_set_decoration
        JOIN decoration_skill
            ON user_set_decoration.decoration_id = decoration_skill.decoration_id
        JOIN skill_tree
            ON decoration_skill.skill_tree_id = skill_tree.id
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = :language
        WHERE user_set_decoration.user_set_id = :equipmentSetId
        GROUP BY skill_tree.id
        """
    )
    suspend fun getDecorationSkillsByUserSetId(
        equipmentSetId: Int,
        language: String
    ): List<EquipmentSkillTreePoint>

    @Query(
        """
        SELECT
            user_set.id AS equipmentId,
            item.*,
            item_text.*,
            SUM(weapon_recipe.quantity) AS quantity
        FROM user_set
        JOIN weapon_recipe
            ON user_set.weapon_id = weapon_recipe.weapon_id
            AND weapon_recipe.recipe_type = :recipeType
        JOIN item
            ON weapon_recipe.item_id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE user_set.id = :equipmentSetId
        GROUP BY item.id
        """
    )
    suspend fun getWeaponRecipeByUserSetId(
        equipmentSetId: Int,
        recipeType: String,
        language: String
    ): List<EquipmentItemQuantity>

    @Query(
        """
        SELECT
            user_set_armor.user_set_id AS equipmentId,
            item.*,
            item_text.*,
            SUM(armor_recipe.quantity) AS quantity
        FROM user_set_armor
        JOIN armor_recipe
            ON user_set_armor.armor_id = armor_recipe.armor_id
        JOIN item
            ON armor_recipe.item_id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE user_set_armor.user_set_id = :equipmentSetId
        GROUP BY item.id
        """
    )
    suspend fun getArmorRecipesByUserSetId(
        equipmentSetId: Int,
        language: String
    ): List<EquipmentItemQuantity>

    @Query(
        """
        SELECT
            user_set_decoration.user_set_id AS equipmentId,
            item.*,
            item_text.*,
            SUM(decoration_recipe.quantity * user_set_decoration.quantity) AS quantity
        FROM user_set_decoration
        JOIN decoration_recipe
            ON user_set_decoration.decoration_id = decoration_recipe.decoration_id
            AND decoration_recipe.recipe_variant = 1
        JOIN item
            ON decoration_recipe.item_id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE user_set_decoration.user_set_id = :equipmentSetId
        GROUP BY item.id
        """
    )
    suspend fun getDecorationRecipesByUserSetId(
        equipmentSetId: Int,
        language: String
    ): List<EquipmentItemQuantity>

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
        val armors = equipmentSetArmors.map { it.copy(userSetId = newEquipmentSetId) }
        val decorations = equipmentSetDecorations.map { it.copy(userSetId = newEquipmentSetId) }

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
